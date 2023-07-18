package com.hvdbs.savra.hhsearchmailservice.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;

@Slf4j
@RequiredArgsConstructor
@Service
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender emailSender;

    @Override
    public void sendMessageWithAttachment(String to, String subject, String text, File file) {
        MimeMessage message = emailSender.createMimeMessage();

        MimeMessageHelper helper;

        try {
            helper = new MimeMessageHelper(message, true);
            helper.setFrom("savra.sv@gmail.com");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text);
            helper.addAttachment("Отчет", file);
        } catch (MessagingException e) {
            log.error("При отправки email произошла ошибка", e);
        }

        emailSender.send(message);
    }
}