package com.example.shortenurl.controllers;

import com.example.shortenurl.dtos.*;
import com.example.shortenurl.models.ShortenedUrl;
import com.example.shortenurl.services.UrlService;
import org.springframework.stereotype.Controller;

@Controller
public class UrlController {

    private UrlService urlService;

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    public ShortenUrlResponseDto shortenUrl(ShortenUrlRequestDto requestDto) {
        ShortenUrlResponseDto responseDto = new ShortenUrlResponseDto();
        try{
            ShortenedUrl inventory = urlService.shortenUrl(requestDto.getOriginalUrl(), requestDto.getUserId());
            responseDto.setShortUrl(inventory.getShortUrl());
            responseDto.setExpiresAt(inventory.getExpiresAt());
            responseDto.setStatus(ResponseStatus.SUCCESS);
            return responseDto;
        } catch (Exception e){
            responseDto.setStatus(ResponseStatus.FAILURE);
            return responseDto;
        }
    }

    public ResolveShortenUrlResponseDto resolveShortenedUrl(ResolveShortenUrlRequestDto requestDto) {
        ResolveShortenUrlResponseDto responseDto = new ResolveShortenUrlResponseDto();
        try{
            String original = urlService.resolveShortenedUrl(requestDto.getShortenUrl());
            responseDto.setOriginalUrl(original);
            responseDto.setStatus(ResponseStatus.SUCCESS);
            return responseDto;
        } catch (Exception e){
            responseDto.setStatus(ResponseStatus.FAILURE);
            return responseDto;
        }
    }
}
