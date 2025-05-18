package com.example.splitwise.controllers;


import com.example.splitwise.dtos.ResponseStatus;
import com.example.splitwise.dtos.SettleGroupRequestDto;
import com.example.splitwise.dtos.SettleGroupResponseDto;
import com.example.splitwise.dtos.SettleUserRequestDto;
import com.example.splitwise.services.SettleUpService;
import org.springframework.stereotype.Controller;

@Controller
public class SettleUpController {
    SettleUpService settleUpService;

    public SettleUpController(SettleUpService settleUpService) {
        this.settleUpService = settleUpService;
    }


    public SettleGroupResponseDto settleGroup(SettleGroupRequestDto dto) {
        SettleGroupResponseDto response = new SettleGroupResponseDto();
        try {
            response.setTransactions(settleUpService.settleGroup(dto.getGroupId()));
            response.setResponseStatus(ResponseStatus.SUCCESS);
        } catch (Exception e) {
            response.setResponseStatus(ResponseStatus.FAILURE);
        }
        return response;
    }

    public SettleGroupResponseDto settleUser(SettleUserRequestDto requestDto) {
        SettleGroupResponseDto resopnse = new SettleGroupResponseDto();
        try {
            resopnse.setTransactions(settleUpService.settleUser(requestDto.getUserId()));
            resopnse.setResponseStatus(ResponseStatus.SUCCESS);
        } catch (Exception e) {
            resopnse.setResponseStatus(ResponseStatus.FAILURE);
        }
        return resopnse;
    }
}
