package com.example.ecom.controllers;

import com.example.ecom.dtos.GetAdvertisementForUserRequestDto;
import com.example.ecom.dtos.GetAdvertisementForUserResponseDto;
import com.example.ecom.dtos.ResponseStatus;
import com.example.ecom.services.AdsService;
import org.springframework.stereotype.Controller;

@Controller
public class AdsController {
    private AdsService adsService;

    public AdsController(AdsService adsService) {
        this.adsService = adsService;
    }

    public GetAdvertisementForUserResponseDto getAdvertisementForUser(GetAdvertisementForUserRequestDto requestDto) {
        GetAdvertisementForUserResponseDto response=new GetAdvertisementForUserResponseDto();
        try{
            response.setAdvertisement(adsService.getAdvertisementForUser(requestDto.getUserId()));
            response.setResponseStatus(ResponseStatus.SUCCESS);
        }catch(Exception e){
            response.setResponseStatus(ResponseStatus.FAILURE);
        }
        return response;
    }
}
