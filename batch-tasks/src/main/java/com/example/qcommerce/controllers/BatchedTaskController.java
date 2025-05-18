package com.example.qcommerce.controllers;

import com.example.qcommerce.dtos.BuildBatchedTaskRouteRequestDto;
import com.example.qcommerce.dtos.BuildBatchedTaskRouteResponseDto;
import com.example.qcommerce.dtos.ResponseStatus;
import com.example.qcommerce.services.BatchedTaskService;
import org.springframework.stereotype.Controller;

@Controller
public class BatchedTaskController {
    BatchedTaskService batchedTaskService;

    public BatchedTaskController(BatchedTaskService batchedTaskService) {
        this.batchedTaskService = batchedTaskService;
    }

    public BuildBatchedTaskRouteResponseDto buildRoute(BuildBatchedTaskRouteRequestDto requestDto) {
        BuildBatchedTaskRouteResponseDto response = new BuildBatchedTaskRouteResponseDto();
        try {
            response.setRouteToBeTaken(batchedTaskService.buildRoute(requestDto.getBatchedTaskId()));
            response.setStatus(ResponseStatus.SUCCESS);
        } catch (Exception e) {
            response.setStatus(ResponseStatus.FAILURE);
        }
        return response;
    }
}
