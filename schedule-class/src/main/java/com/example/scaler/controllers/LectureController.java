package com.example.scaler.controllers;

import com.example.scaler.dtos.ResponseStatus;
import com.example.scaler.dtos.ScheduleLectureRequestDto;
import com.example.scaler.dtos.ScheduleLecturesResponseDto;
import com.example.scaler.services.LectureService;
import org.springframework.stereotype.Controller;

@Controller
public class LectureController {
    private LectureService lectureService;

    public LectureController(LectureService lectureService) {
        this.lectureService = lectureService;
    }


    public ScheduleLecturesResponseDto scheduleLectures(ScheduleLectureRequestDto requestDto) {
        ScheduleLecturesResponseDto responseDto=new ScheduleLecturesResponseDto();
        try{
            responseDto.setScheduledLectures(lectureService.scheduleLectures(requestDto.getLectureIds(), requestDto.getInstructorId(), requestDto.getBatchId()));
            responseDto.setResponseStatus(ResponseStatus.SUCCESS);
        }catch (Exception e){
            responseDto.setResponseStatus(ResponseStatus.FAILURE);
        }

        return responseDto;
    }
}
