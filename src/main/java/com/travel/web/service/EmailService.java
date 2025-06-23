package com.travel.web.service;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(String toEmail, String subject, String body) {
        try {
            MimeMessage message = mailSender.createMimeMessage();

            // true = multipart message
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom("vishalvp9014@gmail.com");  // You can load this from application.properties
            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setText(body, true);  // true = HTML content

            mailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();  // You can add proper logging here
        }
    }
    public String sendOtpEmail(String toEmail) {

        // Generate 6-digit OTP
        String otp = String.format("%06d", new Random().nextInt(999999));

        // Prepare email content (HTML)
        String htmlBody = "<h3>Your OTP Code</h3>" +
                "<p>Hello,</p>" +
                "<p>Your OTP for verification is: <b style='font-size: 24px;'>" + otp + "</b></p>" +
                "<p>This OTP is valid for 5 minutes.</p>" +
                "<p>Thank you!</p>";

        // Send email
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom("vishalvp9014@gmail.com");
            helper.setTo(toEmail);
            helper.setSubject("Your OTP Code");
            helper.setText(htmlBody, true); // HTML body

            mailSender.send(message);

            System.out.println("Sent OTP to " + toEmail + " : " + otp);

            return otp; // Return OTP so you can store in DB / Session / Cache
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
