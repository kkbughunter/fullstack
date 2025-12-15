package com.application.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendTestEmail(String to) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Test Email");
        message.setText("Your Login OTP is 736 857.");
        
        mailSender.send(message);
    }
}