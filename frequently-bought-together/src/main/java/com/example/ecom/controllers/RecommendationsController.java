package com.example.ecom.controllers;

import com.example.ecom.dtos.GenerateRecommendationsRequestDto;
import com.example.ecom.dtos.GenerateRecommendationsResponseDto;
import com.example.ecom.dtos.ResponseStatus;
import com.example.ecom.services.RecommendationsServiceImpl;
import org.springframework.stereotype.Controller;

@Controller
public class RecommendationsController {
    public RecommendationsServiceImpl recommendationsService;

    public RecommendationsController(RecommendationsServiceImpl recommendationsService) {
        this.recommendationsService = recommendationsService;
    }

    public GenerateRecommendationsResponseDto generateRecommendations(GenerateRecommendationsRequestDto requestDto) {
        GenerateRecommendationsResponseDto responseDto = new GenerateRecommendationsResponseDto();
        try {
            responseDto.setRecommendations(recommendationsService.getRecommendations(requestDto.getProductId()));
            responseDto.setResponseStatus(ResponseStatus.SUCCESS);
        } catch (Exception e) {
            responseDto.setResponseStatus(ResponseStatus.FAILURE);
        }
        return responseDto;
    }
}
