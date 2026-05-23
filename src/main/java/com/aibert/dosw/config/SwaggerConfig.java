package com.aibert.dosw.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Profile Service API — AIBERT")
                        .version("1.0.0")
                        .description("Centralizes the user lifecycle: registration, OTP verification, personal and academic profile management, administration and account deletion.\n\n" +
                                "Key responsibilities:\n" +
                                "• R01 — User registration with institutional email validation\n" +
                                "• R02 — Email verification via OTP (6-digit code, 5-minute expiry, 3-attempt limit)\n" +
                                "• R03 — OTP resend functionality\n" +
                                "• R04 — Personal profile updates (username)\n" +
                                "• R05 — Profile photo upload and management\n" +
                                "• R06 — Academic profile management (career, semester, GPA, subjects, goals)\n" +
                                "• R07 — Password change with current password verification\n" +
                                "• R08 — Account deletion with password confirmation\n" +
                                "• R09 — Admin user management (list, edit, status, role, delete)\n\n" +
                                "Authentication: all endpoints require a Bearer JWT token issued by the auth service. Use the Authorize button to set your token.\n\n" +
                                "Local testing: see `swagger-tests/swagger-tests-guide.md` in the repository for ready-to-paste curl commands and a JWT token generation guide.\n\n" +
                                "Contact Profile Management Team"))
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .components(new Components().addSecuritySchemes("bearerAuth",
                        new SecurityScheme()
                                .name("bearerAuth")
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")));
    }
}
