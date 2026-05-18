package com.aibert.dosw.entrypoints.rest.controller;

import com.aibert.dosw.application.dto.request.RegisterRequestDTO;
import com.aibert.dosw.application.dto.response.OtpVerificationResponseDTO;
import com.aibert.dosw.application.dto.response.RegisterResponseDTO;
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
@Tag(name = "Auth", description = "Endpoints for user registration and email verification")
public class AuthController {

    private final RegisterUseCase registerUseCase;

    @Operation(summary = "Register a new user")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "User registered successfully. OTP sent to email."),
            @ApiResponse(responseCode = "400", description = "Invalid fields, weak password, or passwords do not match."),
            @ApiResponse(responseCode = "409", description = "Email is already registered in the system.")
    })
    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDTO> register(@Valid @RequestBody RegisterRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(registerUseCase.register(request));
    }

    @Operation(summary = "Verify OTP code")
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

    @Operation(summary = "Resend verification email")
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
}
