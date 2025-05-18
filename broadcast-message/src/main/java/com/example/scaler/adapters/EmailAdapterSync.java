package com.example.scaler.adapters;

import com.example.scaler.library.sendgrid.Sendgrid;
import org.hibernate.annotations.Comment;
import org.springframework.stereotype.Component;

@Component
public class EmailAdapterSync implements EmailAdapter{
    Sendgrid sendgrid = new Sendgrid();
    @Override
    public void sendEmail(String email, String message) throws Exception {
        sendgrid.sendEmailAsync(email, message);
    }
}
