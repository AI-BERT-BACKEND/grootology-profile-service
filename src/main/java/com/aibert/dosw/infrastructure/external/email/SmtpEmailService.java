package com.aibert.dosw.infrastructure.external.email;

import com.aibert.dosw.domain.ports.out.EmailServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SmtpEmailService implements EmailServicePort {

    private final JavaMailSender mailSender;

    @Override
    public void sendVerificationEmail(String toEmail, String verificationLink) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Verifica tu cuenta en EciPlanner");
        message.setText("Haz clic en el siguiente enlace para verificar tu cuenta (caduca en 24 horas):\n\n" + verificationLink);
        mailSender.send(message);
    }
}
