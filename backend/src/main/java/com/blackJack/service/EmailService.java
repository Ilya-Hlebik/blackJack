package com.blackJack.service;

import com.blackJack.dbo.EmailConfirmation;
import com.blackJack.dbo.User;
import com.blackJack.repository.EmailRepository;
import com.blackJack.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;
    private final EmailRepository emailRepository;
    private final UserRepository userRepository;
    @Value("${server.app.url}")
    private String requestUrl;

    @SneakyThrows
    public ResponseEntity<Void> sendVerifyCodeByEmail(final String email) {
        final Optional<User> byEmail = userRepository.findByEmail(email);
        if (byEmail.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        final String token = UUID.randomUUID().toString();
        emailRepository.deleteById(email);
        final EmailConfirmation emailConfirmation = new EmailConfirmation(email, token, false);
        emailRepository.save(emailConfirmation);

        final MimeMessage message = javaMailSender.createMimeMessage();
        final MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(email);
        helper.setSubject("Blackjack - confirm your password recovery");
        helper.setText("Hello <br/>" +
                "Please click on this link to verify your identity:<br/>\n"
                + "<a href=\"" + requestUrl + "/user/changePassword?token=" + token + "\">Verify Link</a> <br/>" +
                 "Best regards<br/><strong>Blackjack</strong><br/>" +
                "<img src=\"https://user-images.githubusercontent.com/28773654/94368500-00774e00-00ed-11eb-9093-10f96124013c.png\">", true);
        javaMailSender.send(message);
        return ResponseEntity.ok().build();
    }

    public boolean verifyToken(final @Valid String token) {
        final Optional<EmailConfirmation> confirmationOptional = emailRepository.findByToken(token);
        if (confirmationOptional.isPresent()) {
            return confirmationOptional.orElseThrow().getToken().equals(token);
        }
        return false;
    }

    public EmailConfirmation findConfirmationByToken(final String token) {
        return emailRepository.findByToken(token).orElseThrow();
    }
}
