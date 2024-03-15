package com.gmail.voronovskyi.yaroslav.isg.springboot.service.impl;

import com.gmail.voronovskyi.yaroslav.isg.springboot.model.EmailDetails;
import com.gmail.voronovskyi.yaroslav.isg.springboot.service.EmailService;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailServiceImpl implements EmailService {

    @Autowired
    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private  String sender;

    public EmailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void sendSimpleMail(EmailDetails details) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom("");
            mailMessage.setTo(details.getRecipient());
            mailMessage.setText(details.getMsgBody());
            mailMessage.setSubject(details.getSubject());
            javaMailSender.send(mailMessage);
        } catch (Exception exception) {
            throw new RuntimeException();
        }
    }
}
