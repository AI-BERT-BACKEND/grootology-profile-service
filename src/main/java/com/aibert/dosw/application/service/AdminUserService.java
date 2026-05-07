package com.aibert.dosw.application.service;

import com.aibert.dosw.application.dto.request.ChangeRoleRequestDTO;
import com.aibert.dosw.application.dto.response.UserSummaryDTO;
import com.aibert.dosw.domain.exceptions.UserNotFoundException;
import com.aibert.dosw.domain.model.user.User;
import com.aibert.dosw.domain.model.user.UserStatus;
import com.aibert.dosw.domain.ports.in.AdminUserUseCase;
import com.aibert.dosw.domain.ports.out.UserRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminUserService implements AdminUserUseCase {

    private final UserRepositoryPort userRepository;

    @Override
    public List<UserSummaryDTO> listUsers(String name, String email, String status) {
        UserStatus userStatus = status != null ? UserStatus.valueOf(status) : null;
        return userRepository.findByFilters(name, email, userStatus)
                .stream().map(this::toSummary).collect(Collectors.toList());
    }

    @Override
    public UserSummaryDTO getUserDetail(UUID userId) {
        return toSummary(userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new));
    }

    @Override
    public UserSummaryDTO updateUserStatus(UUID adminId, UUID userId, String newStatus) {
        if (adminId.equals(userId)) {
            throw new IllegalArgumentException("No puedes realizar esta acción sobre tu propia cuenta");
        }
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        User updated = copyWith(user, u -> u.status(UserStatus.valueOf(newStatus)));
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
