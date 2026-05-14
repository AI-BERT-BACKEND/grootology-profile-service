package com.aibert.dosw.application.service;

import com.aibert.dosw.application.dto.request.RegisterRequestDTO;
import com.aibert.dosw.application.dto.response.OtpVerificationResponseDTO;
import com.aibert.dosw.application.dto.response.RegisterResponseDTO;
import com.aibert.dosw.domain.exceptions.EmailAlreadyRegisteredException;
import com.aibert.dosw.domain.exceptions.InvalidTokenException;
import com.aibert.dosw.domain.exceptions.UserNotFoundException;
import com.aibert.dosw.domain.model.user.EmailVerificationToken;
import com.aibert.dosw.domain.model.user.Role;
import com.aibert.dosw.domain.model.user.User;
import com.aibert.dosw.domain.ports.in.RegisterUseCase;
import com.aibert.dosw.domain.ports.out.EmailServicePort;
import com.aibert.dosw.domain.ports.out.TokenRepositoryPort;
import com.aibert.dosw.domain.ports.out.UserRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
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
                .role(Role.ESTUDIANTE)
                .status(com.aibert.dosw.domain.model.user.UserStatus.ACTIVO)
                .career(request.getCareer())
                .profileComplete(false)
                .createdAt(LocalDateTime.now())
                .build());

        sendVerificationToken(user);
        return RegisterResponseDTO.builder()
                .id(user.getId())
                .role(user.getRole().name())
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
                .role(user.getRole())
                .status(user.getStatus())
                .career(user.getCareer())
                .currentSemester(user.getCurrentSemester())
                .weeklyHours(user.getWeeklyHours())
                .profileComplete(user.isProfileComplete())
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

    private static final int MAX_OTP_ATTEMPTS = 3;
    private static final int OTP_LOCK_MINUTES = 15;
    private static final int OTP_EXPIRY_MINUTES = 5;
    private static final SecureRandom RANDOM = new SecureRandom();

    private void sendVerificationToken(User user) {
        String otp = String.format("%06d", RANDOM.nextInt(1_000_000));
        tokenRepository.save(EmailVerificationToken.builder()
                .token(otp)
                .userId(user.getId())
                .expiresAt(LocalDateTime.now().plusMinutes(OTP_EXPIRY_MINUTES))
                .used(false)
                .failedAttempts(0)
                .build());
        emailService.sendVerificationEmail(user.getEmail(), otp);
    }

    @Override
    public OtpVerificationResponseDTO verifyOtp(UUID userId, String otp) {
        EmailVerificationToken token = tokenRepository.findLatestByUserId(userId)
                .orElseThrow(InvalidTokenException::new);

        if (token.getBlockedUntil() != null && LocalDateTime.now().isBefore(token.getBlockedUntil())) {
            long secondsLeft = ChronoUnit.SECONDS.between(LocalDateTime.now(), token.getBlockedUntil());
            return OtpVerificationResponseDTO.builder()
                    .verificationStatus(false)
                    .accountStatus(false)
                    .expirationTime(secondsLeft)
                    .resendAvailability(false)
                    .build();
        }

        if (token.isUsed() || token.getExpiresAt().isBefore(LocalDateTime.now())) {
            return OtpVerificationResponseDTO.builder()
                    .verificationStatus(false)
                    .accountStatus(false)
                    .expirationTime(0)
                    .resendAvailability(true)
                    .build();
        }

        if (!token.getToken().equals(otp)) {
            int attempts = (token.getFailedAttempts() == null ? 0 : token.getFailedAttempts()) + 1;
            LocalDateTime blockedUntil = attempts >= MAX_OTP_ATTEMPTS
                    ? LocalDateTime.now().plusMinutes(OTP_LOCK_MINUTES) : null;
            tokenRepository.save(EmailVerificationToken.builder()
                    .id(token.getId())
                    .token(token.getToken())
                    .userId(token.getUserId())
                    .expiresAt(token.getExpiresAt())
                    .used(false)
                    .failedAttempts(attempts >= MAX_OTP_ATTEMPTS ? 0 : attempts)
                    .blockedUntil(blockedUntil)
                    .build());
            return OtpVerificationResponseDTO.builder()
                    .verificationStatus(false)
                    .accountStatus(false)
                    .expirationTime(ChronoUnit.SECONDS.between(LocalDateTime.now(), token.getExpiresAt()))
                    .resendAvailability(blockedUntil == null)
                    .build();
        }

        User user = userRepository.findById(token.getUserId()).orElseThrow(UserNotFoundException::new);
        userRepository.save(User.builder()
                .id(user.getId()).fullName(user.getFullName()).email(user.getEmail())
                .password(user.getPassword()).verified(true).role(user.getRole())
                .status(user.getStatus()).career(user.getCareer())
                .currentSemester(user.getCurrentSemester()).weeklyHours(user.getWeeklyHours())
                .profileComplete(user.isProfileComplete()).profilePhotoUrl(user.getProfilePhotoUrl())
                .createdAt(user.getCreatedAt()).build());

        tokenRepository.save(EmailVerificationToken.builder()
                .id(token.getId()).token(token.getToken()).userId(token.getUserId())
                .expiresAt(token.getExpiresAt()).used(true).failedAttempts(0).build());

        return OtpVerificationResponseDTO.builder()
                .verificationStatus(true)
                .accountStatus(true)
                .expirationTime(0)
                .resendAvailability(false)
                .build();
    }
