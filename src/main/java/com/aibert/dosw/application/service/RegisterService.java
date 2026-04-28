package com.aibert.dosw.application.service;

import com.aibert.dosw.application.dto.request.RegisterRequestDTO;
import com.aibert.dosw.domain.exceptions.EmailAlreadyRegisteredException;
import com.aibert.dosw.domain.exceptions.InvalidTokenException;
import com.aibert.dosw.domain.exceptions.UserNotFoundException;
import com.aibert.dosw.domain.model.user.EmailVerificationToken;
import com.aibert.dosw.domain.model.user.User;
import com.aibert.dosw.application.dto.response.RegisterResponseDTO;
import com.aibert.dosw.domain.ports.in.RegisterUseCase;
import com.aibert.dosw.domain.ports.out.EmailServicePort;
import com.aibert.dosw.domain.ports.out.TokenRepositoryPort;
import com.aibert.dosw.domain.ports.out.UserRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RegisterService implements RegisterUseCase {

    private final UserRepositoryPort userRepository;
    private final TokenRepositoryPort tokenRepository;
    private final EmailServicePort emailService;
    private final BCryptPasswordEncoder passwordEncoder;

    @Value("${app.base-url}")
    private String baseUrl;

    @Override
    public RegisterResponseDTO register(RegisterRequestDTO request) {
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new IllegalArgumentException("Las contraseñas no coinciden");
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyRegisteredException();
        }

        User user = userRepository.save(User.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .verified(false)
                .termsAccepted(request.isTermsAccepted())
                .createdAt(LocalDateTime.now())
                .build());

        sendVerificationToken(user);
        return RegisterResponseDTO.builder()
                .id(user.getId())
                .message("Registro exitoso. Revisa tu correo para verificar tu cuenta.")
                .build();
    }

    @Override
    public void verifyEmail(String token) {
        EmailVerificationToken verificationToken = tokenRepository.findByToken(token)
                .orElseThrow(InvalidTokenException::new);

        if (verificationToken.isUsed() || verificationToken.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new InvalidTokenException();
        }

        User user = userRepository.findById(verificationToken.getUserId())
                .orElseThrow(UserNotFoundException::new);

        userRepository.save(User.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .password(user.getPassword())
                .verified(true)
                .termsAccepted(user.isTermsAccepted())
                .career(user.getCareer())
                .currentSemester(user.getCurrentSemester())
                .weeklyHours(user.getWeeklyHours())
                .profilePhotoUrl(user.getProfilePhotoUrl())
                .createdAt(user.getCreatedAt())
                .build());

        tokenRepository.save(EmailVerificationToken.builder()
                .id(verificationToken.getId())
                .token(verificationToken.getToken())
                .userId(verificationToken.getUserId())
                .expiresAt(verificationToken.getExpiresAt())
                .used(true)
                .build());
    }

    @Override
    public void resendVerificationEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(UserNotFoundException::new);
        sendVerificationToken(user);
    }

    private void sendVerificationToken(User user) {
        String token = UUID.randomUUID().toString();
        tokenRepository.save(EmailVerificationToken.builder()
                .token(token)
                .userId(user.getId())
                .expiresAt(LocalDateTime.now().plusHours(24))
                .used(false)
                .build());
        emailService.sendVerificationEmail(user.getEmail(), baseUrl + "/api/auth/verify?token=" + token);
    }
}
