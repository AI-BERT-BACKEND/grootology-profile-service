package com.aibert.dosw.config;

import com.aibert.dosw.infrastructure.external.email.SmtpEmailService;
import io.swagger.v3.oas.models.OpenAPI;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConfigAndEmailTest {

    @Mock private JavaMailSender mailSender;
    @InjectMocks private SmtpEmailService smtpEmailService;

    @Test
    void sendVerificationEmail_enviaCorreo() {
        ReflectionTestUtils.setField(smtpEmailService, "fromEmail", "noreply@test.com");
        doNothing().when(mailSender).send(any(SimpleMailMessage.class));

        smtpEmailService.sendVerificationEmail("user@test.com", "123456");

        ArgumentCaptor<SimpleMailMessage> captor = ArgumentCaptor.forClass(SimpleMailMessage.class);
        verify(mailSender).send(captor.capture());
        SimpleMailMessage msg = captor.getValue();
        assertArrayEquals(new String[]{"user@test.com"}, msg.getTo());
        assertEquals("noreply@test.com", msg.getFrom());
        assertTrue(msg.getText().contains("123456"));
    }

    @Test
    void sendRecoveryEmail_enviaCorreo() {
        ReflectionTestUtils.setField(smtpEmailService, "fromEmail", "noreply@test.com");
        doNothing().when(mailSender).send(any(SimpleMailMessage.class));

        smtpEmailService.sendRecoveryEmail("user@test.com", "http://reset-link");

        ArgumentCaptor<SimpleMailMessage> captor = ArgumentCaptor.forClass(SimpleMailMessage.class);
        verify(mailSender).send(captor.capture());
        SimpleMailMessage msg = captor.getValue();
        assertArrayEquals(new String[]{"user@test.com"}, msg.getTo());
        assertTrue(msg.getText().contains("http://reset-link"));
    }

    @Test
    void securityConfig_passwordEncoder_retornaBCrypt() {
        SecurityConfig config = new SecurityConfig();
        BCryptPasswordEncoder encoder = config.passwordEncoder();
        assertNotNull(encoder);
        assertTrue(encoder.matches("test", encoder.encode("test")));
    }

    @Test
    void swaggerConfig_openAPI_retornaOpenAPI() {
        SwaggerConfig config = new SwaggerConfig();
        OpenAPI openAPI = config.openAPI();
        assertNotNull(openAPI);
        assertNotNull(openAPI.getInfo());
        assertEquals("Profile Service API — AIBERT", openAPI.getInfo().getTitle());
    }
}
