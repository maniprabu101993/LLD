package com.example.bmsbookticket.controllers;

import com.example.bmsbookticket.dtos.*;
import com.example.bmsbookticket.models.Rating;
import com.example.bmsbookticket.services.RatingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class RatingsController {
    @Autowired
    private RatingsService ratingsService;

    public RateMovieResponseDto rateMovie(RateMovieRequestDto requestDto) {
        RateMovieResponseDto reponse = new RateMovieResponseDto();
        try {
            Rating rating = ratingsService.rateMovie(requestDto.getUserId(), requestDto.getMovieId(), requestDto.getRating());
            reponse.setResponseStatus(ResponseStatus.SUCCESS);
            reponse.setRating(rating);
        } catch (Exception e) {
            reponse.setResponseStatus(ResponseStatus.FAILURE);
        }
        return reponse;
    }

    public GetAverageMovieResponseDto getAverageMovieRating(GetAverageMovieRequestDto requestDto) {
        GetAverageMovieResponseDto reponse = new GetAverageMovieResponseDto();
        try {
            double rating = ratingsService.getAverageRating(requestDto.getMovieId());
            reponse.setResponseStatus(ResponseStatus.SUCCESS);
            reponse.setAverageRating(rating);
        } catch (Exception e) {
            reponse.setResponseStatus(ResponseStatus.FAILURE);
        }
        return reponse;
    }
}
