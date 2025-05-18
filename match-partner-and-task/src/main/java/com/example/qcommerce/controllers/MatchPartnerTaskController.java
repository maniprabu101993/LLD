package com.example.qcommerce.controllers;

import com.example.qcommerce.dtos.MatchPartnerTaskRequestDto;
import com.example.qcommerce.dtos.MatchPartnerTaskResponseDto;
import com.example.qcommerce.dtos.ResponseStatus;
import com.example.qcommerce.services.MatchPartnerTaskService;
import org.springframework.stereotype.Controller;

@Controller
public class MatchPartnerTaskController {
    private MatchPartnerTaskService matchPartnerTaskService;

    public MatchPartnerTaskController(MatchPartnerTaskService matchPartnerTaskService) {
        this.matchPartnerTaskService = matchPartnerTaskService;
    }

    public MatchPartnerTaskResponseDto matchPartnersAndTasks(MatchPartnerTaskRequestDto requestDto) {
        MatchPartnerTaskResponseDto responseDto = new MatchPartnerTaskResponseDto();
        try {
            responseDto.setResponseStatus(ResponseStatus.SUCCESS);
            responseDto.setPartnerTaskMappings(matchPartnerTaskService.matchPartnersAndTasks(requestDto.getPartnerIds(), requestDto.getTaskIds()));
        } catch (Exception e) {
            responseDto.setResponseStatus(ResponseStatus.FAILURE);
        }
        return responseDto;
    }
}
