package com.example.bmsbookticket.services;

import com.example.bmsbookticket.models.User;
import org.springframework.stereotype.Service;

public interface UserService {

    public User signupUser(String name, String email, String password) throws Exception;

    public boolean login(String email, String password) throws Exception;
}
