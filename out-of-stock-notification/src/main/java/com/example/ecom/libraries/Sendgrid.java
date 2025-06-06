package com.example.ecom.libraries;

import org.springframework.stereotype.Service;

@Service
public class Sendgrid {

    public void sendEmailAsync(String email, String subject, String body) {
        // Code to send email using Sendgrid backend API
        System.out.println("Sending email to " + email + " with subject " + subject + " and body " + body);
    }
}
