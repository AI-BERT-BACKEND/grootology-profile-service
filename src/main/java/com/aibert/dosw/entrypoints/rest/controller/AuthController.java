package com.aibert.dosw.entrypoints.rest.controller;

import com.aibert.dosw.application.dto.request.RegisterRequestDTO;
import com.aibert.dosw.application.dto.response.OtpVerificationResponseDTO;
import com.aibert.dosw.application.dto.response.PasswordResetResponseDTO;
import com.aibert.dosw.application.dto.response.RegisterResponseDTO;
import com.aibert.dosw.domain.ports.in.PasswordResetUseCase;
import com.aibert.dosw.domain.ports.in.RegisterUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Auth", description = "Endpoints for user registration, email verification, and password recovery")
public class AuthController {

    private final RegisterUseCase registerUseCase;
    private final PasswordResetUseCase passwordResetUseCase;

    @Operation(
            summary = "Register a new user",
            description = "Creates a new user account with the provided information. The email must be an institutional address (@mail.escuelaing.edu.co). After registration, a 6-digit OTP code is sent to the user's email for account verification."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "User registered successfully. OTP sent to email."),
            @ApiResponse(responseCode = "400", description = "Invalid fields, weak password, or passwords do not match."),
            @ApiResponse(responseCode = "409", description = "Email is already registered in the system.")
    })
    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDTO> register(@Valid @RequestBody RegisterRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(registerUseCase.register(request));
    }

    @Operation(
            summary = "Verify OTP code",
            description = "Activates the user account by validating the 6-digit OTP code sent to the registered email. The code expires after a limited time. Once verified, the user can log in through the Auth Service."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Account verified successfully."),
            @ApiResponse(responseCode = "400", description = "Invalid or expired OTP code."),
            @ApiResponse(responseCode = "404", description = "User not found.")
    })
    @PostMapping("/verify-otp")
    public ResponseEntity<OtpVerificationResponseDTO> verifyOtp(
            @Parameter(description = "ID of the user to verify") @RequestParam UUID userId,
            @Parameter(description = "6-digit OTP code sent to the user's email") @RequestParam String otp) {
        return ResponseEntity.ok(registerUseCase.verifyOtp(userId, otp));
    }

    @Operation(
            summary = "Resend verification email",
            description = "Sends a new OTP code to the user's registered email. Useful if the previous code expired or was not received."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "New OTP code sent successfully."),
            @ApiResponse(responseCode = "404", description = "No account found with the provided email.")
    })
    @PostMapping("/resend-verification")
    public ResponseEntity<String> resend(
            @Parameter(description = "Email address of the account to resend the code to") @RequestParam String email) {
        registerUseCase.resendVerificationEmail(email);
        return ResponseEntity.ok("Código OTP reenviado al correo institucional.");
    }

    @Operation(
            summary = "Request password reset",
            description = "Sends a password reset link to the user's registered email. The link contains a temporary token that expires after a limited time."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Reset email sent successfully."),
            @ApiResponse(responseCode = "404", description = "No account found with the provided email.")
    })
    @PostMapping("/forgot-password")
    public ResponseEntity<PasswordResetResponseDTO> forgotPassword(
            @Parameter(description = "Email address of the account to reset the password for") @RequestParam String email) {
        return ResponseEntity.ok(passwordResetUseCase.requestPasswordReset(email));
    }

    @Operation(
            summary = "Reset password",
            description = "Sets a new password for the user using the token received by email. The new password and confirmation must match, and the password must meet the security requirements: at least 8 characters, one uppercase letter, one lowercase letter, and one number."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Password reset successfully."),
            @ApiResponse(responseCode = "400", description = "Passwords do not match or do not meet security requirements."),
            @ApiResponse(responseCode = "403", description = "Token is invalid or has expired.")
    })
    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(
            @Parameter(description = "Reset token received by email") @RequestParam String token,
            @Parameter(description = "New password") @RequestParam String newPassword,
            @Parameter(description = "New password confirmation") @RequestParam String confirmPassword) {
        passwordResetUseCase.resetPassword(token, newPassword, confirmPassword);
        return ResponseEntity.ok("Contraseña restablecida exitosamente.");
    }
}
