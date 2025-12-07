package com.woofly.courseservice.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;

@Service
@RequiredArgsConstructor
public class EmailService {

    private static final Logger log = LoggerFactory.getLogger(EmailService.class);
    private final JavaMailSender mailSender;

    public void sendEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        try {
            mailSender.send(message);
            log.info("Plain text email sent successfully to {} with subject: {} and body snippet: {}", to, subject, body.substring(0, Math.min(body.length(), 100)));
        } catch (MailException e) {
            log.error("Failed to send plain text email to {} with subject: {}. Error: {}", to, subject, e.getMessage());
        }
    }

    public void sendHtmlEmail(String to, String subject, String htmlContent) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlContent, true); // true for HTML content

            mailSender.send(mimeMessage);
            log.info("HTML Email sent successfully to {} with subject: {} (Body snippet: {})", to, subject, htmlContent.substring(0, Math.min(htmlContent.length(), 100)));
        } catch (MailException e) {
            log.error("Failed to send HTML email to {} with subject: {}. Error: {}", to, subject, e.getMessage());
        } catch (jakarta.mail.MessagingException e) {
            log.error("Failed to construct MimeMessage for email to {} with subject: {}. Error: {}", to, subject, e.getMessage());
        }
    }
}
