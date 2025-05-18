package com.example.scaler.controllers;

import com.example.scaler.dtos.*;
import com.example.scaler.services.MCQExamService;
import org.springframework.stereotype.Controller;

@Controller
public class MCQExamController {
    private MCQExamService learnerService;

    public MCQExamController(MCQExamService learnerService) {
        this.learnerService = learnerService;
    }

    public StartExamResponseDto startExam(StartExamRequestDto requestDto) {
        StartExamResponseDto responseDto = new StartExamResponseDto();
        try {
            responseDto.setLearnerExam(learnerService.startExam(requestDto.getLearnerId(),requestDto.getExamId()));
            responseDto.setResponseStatus(ResponseStatus.SUCCESS);
        } catch (Exception e) {
            responseDto.setResponseStatus(ResponseStatus.FAILURE);
        }
        return responseDto;
    }

    public SubmitExamResponseDto submitExam(SubmitExamRequestDto requestDto) {
        SubmitExamResponseDto responseDto = new SubmitExamResponseDto();
        try {
            responseDto.setLearnerExam(learnerService.submitExam(requestDto.getLearnerId(),requestDto.getExamId()));
            responseDto.setResponseStatus(ResponseStatus.SUCCESS);
        } catch (Exception e) {
            responseDto.setResponseStatus(ResponseStatus.FAILURE);
        }
        return responseDto;
    }

    public AnswerQuestionResponseDto answerQuestion(AnswerQuestionRequestDto requestDto) {
        AnswerQuestionResponseDto responseDto = new AnswerQuestionResponseDto();
        try {
            responseDto.setResponse(learnerService.answerQuestion(requestDto.getLearnerId(),requestDto.getQuestionId(),requestDto.getOptionId()));
            responseDto.setResponseStatus(ResponseStatus.SUCCESS);
        } catch (Exception e) {
            responseDto.setResponseStatus(ResponseStatus.FAILURE);
        }
        return responseDto;
    }

}
