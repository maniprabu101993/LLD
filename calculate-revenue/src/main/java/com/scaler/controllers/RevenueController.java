package com.scaler.controllers;

import com.scaler.dtos.CalculateRevenueRequestDto;
import com.scaler.dtos.CalculateRevenueResponseDto;
import com.scaler.dtos.ResponseStatus;
import com.scaler.models.AggregatedRevenue;
import com.scaler.services.RevenueService;

public class RevenueController {

    private RevenueService revenueService;

    public RevenueController(RevenueService revenueService) {
        this.revenueService = revenueService;
    }

    public CalculateRevenueResponseDto calculateRevenue(CalculateRevenueRequestDto requestDto) {
        CalculateRevenueResponseDto reponse = new CalculateRevenueResponseDto();
        try {
            AggregatedRevenue revenue = revenueService.calculateRevenue(requestDto.getUserId(), requestDto.getRevenueQueryType());
            reponse.setAggregatedRevenue(revenue);
            reponse.setResponseStatus(ResponseStatus.SUCCESS);
        } catch (Exception e) {
            reponse.setResponseStatus(ResponseStatus.FAILURE);
        }
        return reponse;
    }
}
