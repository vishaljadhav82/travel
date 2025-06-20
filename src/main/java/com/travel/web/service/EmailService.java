package com.travel.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public String sendOTP(String toEmail) {
        // Generate 6-digit OTP
        String otp = String.format("%06d", new Random().nextInt(999999));

        // Prepare Email message
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("vishalvp9014@gmail.com");
        message.setTo(toEmail);
        message.setSubject("Your OTP Code");
        message.setText("Your OTP code is: " + otp);

        // Send Email
        mailSender.send(message);

        return otp; // You can store this OTP in DB/session for later verification
    }
}
