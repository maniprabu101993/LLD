package com.example.scaler.controllers;

import com.example.scaler.dtos.BroadcastMessageRequestDto;
import com.example.scaler.dtos.BroadcastMessageResponseDto;
import com.example.scaler.dtos.ResponseStatus;
import com.example.scaler.services.CommunicationService;
import org.springframework.stereotype.Controller;

@Controller
public class CommunicationController {
    private CommunicationService service;

    public CommunicationController(CommunicationService service) {
        this.service = service;
    }


    public BroadcastMessageResponseDto broadcastMessage(BroadcastMessageRequestDto requestDto) {
        BroadcastMessageResponseDto responseDto = new BroadcastMessageResponseDto();
        try {
            responseDto.setCommunication(service.broadcastMessage(requestDto.getBatchId(), requestDto.getUserId(), requestDto.getMessage()));
            responseDto.setStatus(ResponseStatus.SUCCESS);
        } catch (Exception e) {
            responseDto.setStatus(ResponseStatus.FAILURE);
        }
        return responseDto;
    }
}
