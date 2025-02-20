package com.englishaoe.lesson.services.email;

import com.englishaoe.lesson.dto.account.CustomerRegistrationDTO;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private ResourceLoader resourceLoader;
    @Value("${YANDEX_MAIL_USERNAME}")
    String sender;
    @Value("${MAIL_REDIRECT_URL}")
    private String mailRedirectUrl;
    public void sendRegistrationMessage(String to, String subject, String text) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(to);
        mailMessage.setFrom(sender);
        mailMessage.setSubject(subject);
        mailMessage.setText(text);
        mailSender.send(mailMessage);
    }
    public void sendPageRegistrationMessage(String to, String subject, String pageContent) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(to);
        helper.setFrom(sender);
        helper.setSubject(subject);
        helper.setText(pageContent, true);
        mailSender.send(message);
    }
    private String createRedirectUrlForCustomer(String token) {
        return String.format(mailRedirectUrl, token);
    }
    public String assemblyEmailRegistrationText(String customerName, String token) throws IOException {
        Path pathToTemplate = resourceLoader
                .getResource("classpath:templates/confirmation_email_template.html").getFile().toPath();
        String template = new String(Files.readAllBytes((pathToTemplate)));
        return String.format(template, customerName, String.format(mailRedirectUrl, token));
    }
}
