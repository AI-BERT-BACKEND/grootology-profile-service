package com.aibert.dosw.application.service;

import com.aibert.dosw.application.dto.request.AdminEditUserRequestDTO;
import com.aibert.dosw.application.dto.request.ChangeRoleRequestDTO;
import com.aibert.dosw.application.dto.response.UserSummaryDTO;
import com.aibert.dosw.domain.exceptions.UserNotFoundException;
import com.aibert.dosw.domain.model.user.Role;
import com.aibert.dosw.domain.model.user.User;
import com.aibert.dosw.domain.model.user.UserStatus;
import com.aibert.dosw.domain.ports.in.AdminUserUseCase;
import com.aibert.dosw.domain.ports.out.AuditLogPort;
import com.aibert.dosw.domain.ports.out.UserRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminUserService implements AdminUserUseCase {

    private final UserRepositoryPort userRepository;
    private final AuditLogPort auditLogPort;

    @Override
    public List<UserSummaryDTO> listUsers(String name, String email, String status, String role) {
        UserStatus userStatus = status != null ? UserStatus.valueOf(status) : null;
        return userRepository.findByFilters(name, email, userStatus, role)
                .stream().map(this::toSummary).collect(Collectors.toList());
    }

    @Override
    public UserSummaryDTO getUserDetail(UUID userId) {
        return toSummary(userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new));
    }

    @Override
    public UserSummaryDTO editUser(UUID adminId, UUID userId, AdminEditUserRequestDTO request) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        if (user.getStatus() == UserStatus.INACTIVO) {
            throw new IllegalArgumentException("No se puede editar un usuario inactivo. Actívalo primero");
        }
        if (userRepository.existsByEmailAndIdNot(request.getEmail(), userId)) {
            throw new IllegalArgumentException("El correo ya está asociado a otra cuenta");
        }

        List<String> changedFields = new ArrayList<>();
        StringBuilder previousValues = new StringBuilder("{");
        if (!user.getFullName().equals(request.getFullName())) {
            changedFields.add("fullName");
            previousValues.append("\"fullName\":\"").append(user.getFullName()).append("\",");
        }
        if (!user.getEmail().equals(request.getEmail())) {
            changedFields.add("email");
            previousValues.append("\"email\":\"").append(user.getEmail()).append("\",");
        }
        if (previousValues.length() > 1) {
            previousValues.deleteCharAt(previousValues.length() - 1);
        }
        previousValues.append("}");

        User updated = copyWith(user, u -> u
                .fullName(request.getFullName())
                .email(request.getEmail()));
        UserSummaryDTO result = toSummary(userRepository.save(updated));

        if (!changedFields.isEmpty()) {
            try {
                auditLogPort.save(adminId, userId, String.join(",", changedFields),
                        previousValues.toString(), LocalDateTime.now());
            } catch (Exception ignored) {}
        }
        return result;
    }

    @Override
    public UserSummaryDTO updateUserStatus(UUID adminId, UUID userId, String newStatus) {
        if (adminId.equals(userId)) {
            throw new IllegalArgumentException("No puedes realizar esta acción sobre tu propia cuenta");
        }
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        UserStatus status = UserStatus.valueOf(newStatus);
        // Al desactivar, incrementar passwordVersion para invalidar JWTs activos (AIB-8.4 RN-02)
        Integer newPasswordVersion = user.getPasswordVersion();
        if (status == UserStatus.INACTIVO) {
            newPasswordVersion = (newPasswordVersion == null ? 0 : newPasswordVersion) + 1;
        }
        final Integer finalPasswordVersion = newPasswordVersion;
        User updated = copyWith(user, u -> u.status(status).passwordVersion(finalPasswordVersion));
        return toSummary(userRepository.save(updated));
    }

    @Override
    public void deleteUser(UUID adminId, UUID userId) {
        if (adminId.equals(userId)) {
            throw new IllegalArgumentException("No puedes realizar esta acción sobre tu propia cuenta");
        }
        userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        userRepository.deleteById(userId);
    }

    @Override
    public UserSummaryDTO changeRole(UUID adminId, UUID userId, ChangeRoleRequestDTO request) {
        if (adminId.equals(userId)) {
            throw new IllegalArgumentException("No puedes modificar tu propio rol");
        }
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        if (request.getNewRole() == Role.ADMIN && user.getRole() == Role.ADMIN) {
            throw new IllegalArgumentException("El usuario ya tiene el rol ADMIN");
        }
        if (request.getNewRole() == Role.ESTUDIANTE && user.getRole() == Role.ESTUDIANTE) {
            throw new IllegalArgumentException("El usuario ya tiene el rol ESTUDIANTE");
        }
        User updated = copyWith(user, u -> u.role(request.getNewRole()));
        return toSummary(userRepository.save(updated));
    }

    private User copyWith(User user, java.util.function.UnaryOperator<User.UserBuilder> modifier) {
        User.UserBuilder builder = User.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .password(user.getPassword())
                .verified(user.isVerified())
                .role(user.getRole())
                .status(user.getStatus())
                .career(user.getCareer())
                .currentSemester(user.getCurrentSemester())
                .weeklyHours(user.getWeeklyHours())
                .currentGpa(user.getCurrentGpa())
                .currentSubjects(user.getCurrentSubjects())
                .academicGoal(user.getAcademicGoal())
                .currentlyWorking(user.isCurrentlyWorking())
                .profileComplete(user.isProfileComplete())
                .profilePhotoUrl(user.getProfilePhotoUrl())
                .userName(user.getUserName())
                .passwordVersion(user.getPasswordVersion())
                .createdAt(user.getCreatedAt());
        return modifier.apply(builder).build();
    }

    private UserSummaryDTO toSummary(User user) {
        return UserSummaryDTO.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .role(user.getRole())
                .status(user.getStatus())
                .createdAt(user.getCreatedAt())
                .build();
    }
}
