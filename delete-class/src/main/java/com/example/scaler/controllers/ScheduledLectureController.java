package com.example.scaler.controllers;

import com.example.scaler.dtos.RescheduleScheduledLectureRequestDto;
import com.example.scaler.dtos.RescheduleScheduledLectureResponseDto;
import com.example.scaler.dtos.ResponseStatus;
import com.example.scaler.repositories.BatchRepository;
import com.example.scaler.repositories.InstructorRepository;
import com.example.scaler.repositories.LectureRepository;
import com.example.scaler.repositories.ScheduledLectureRepository;
import com.example.scaler.services.ScheduleServiceImpl;
import com.example.scaler.services.ScheduledLectureService;
import org.springframework.stereotype.Controller;

@Controller
public class ScheduledLectureController {
    ScheduledLectureService schedulerserviceimpl;

    public ScheduledLectureController(ScheduledLectureService service) {
        this.schedulerserviceimpl = service;
    }

    public RescheduleScheduledLectureResponseDto rescheduleScheduledLecture(RescheduleScheduledLectureRequestDto requestDto) {
        RescheduleScheduledLectureResponseDto response = new RescheduleScheduledLectureResponseDto();
        try {
            response.setScheduledLectures(schedulerserviceimpl.rescheduleScheduledLecture(requestDto.getScheduledLectureId()));
            response.setResponseStatus(ResponseStatus.SUCCESS);
        } catch (Exception e) {
            response.setResponseStatus(ResponseStatus.FAILURE);
        }
        return response;
    }
}
