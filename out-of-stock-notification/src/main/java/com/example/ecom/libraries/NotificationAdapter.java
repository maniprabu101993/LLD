package com.example.ecom.libraries;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationAdapter {
    @Autowired
    Sendgrid send;

    public void sendNotification(String message, String email) {
        send.sendEmailAsync(email, "Notification", message);
    }
}
