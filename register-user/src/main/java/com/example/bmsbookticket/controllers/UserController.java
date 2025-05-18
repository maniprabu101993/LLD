package com.example.bmsbookticket.controllers;

import com.example.bmsbookticket.dtos.*;
import com.example.bmsbookticket.models.User;
import com.example.bmsbookticket.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class UserController {
    private UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    public SignupUserResponseDTO signupUser(SignupUserRequestDTO requestDTO) {
        SignupUserResponseDTO response = new SignupUserResponseDTO();
        try {
            User usr = service.signupUser(requestDTO.getName(), requestDTO.getEmail(), requestDTO.getPassword());
            response.setResponseStatus(ResponseStatus.SUCCESS);
            response.setUserId(usr.getId());
            response.setName(usr.getName());
            response.setEmail(usr.getEmail());
        } catch (Exception e) {
            response.setResponseStatus(ResponseStatus.FAILURE);
        }
        return response;
    }

    public LoginResponseDto login(LoginRequestDto requestDto) {
        LoginResponseDto response = new LoginResponseDto();
        try {
            boolean successfull = service.login(requestDto.getEmail(), requestDto.getPassword());
            response.setResponseStatus(ResponseStatus.SUCCESS);
            response.setLoggedIn(successfull);
        } catch (Exception e) {
            response.setResponseStatus(ResponseStatus.FAILURE);
        }
        return response;
    }

}
