package com.example.ecom.controllers;

import com.example.ecom.dtos.*;
import com.example.ecom.services.GiftCardService;
import org.springframework.stereotype.Controller;

@Controller
public class GiftCardController {
    private GiftCardService service;

    public GiftCardController(GiftCardService service) {
        this.service = service;
    }


    public CreateGiftCardResponseDto createGiftCard(CreateGiftCardRequestDto requestDto) {
        CreateGiftCardResponseDto responseDto = new CreateGiftCardResponseDto();
        try{
            responseDto.setGiftCard(service.createGiftCard(requestDto.getAmount()));
            responseDto.setResponseStatus(ResponseStatus.SUCCESS);
        }catch (Exception e){
            responseDto.setResponseStatus(ResponseStatus.FAILURE);
        }
        return responseDto;
    }

    public RedeemGiftCardResponseDto redeemGiftCard(RedeemGiftCardRequestDto requestDto) {
        RedeemGiftCardResponseDto responseDto=new RedeemGiftCardResponseDto();
        try{
            responseDto.setGiftCard(service.redeemGiftCard(requestDto.getGiftCardId(),requestDto.getAmountToRedeem()));
            responseDto.setResponseStatus(ResponseStatus.SUCCESS);
        }catch (Exception e){
            responseDto.setResponseStatus(ResponseStatus.FAILURE);
        }
        return responseDto;
    }
}
