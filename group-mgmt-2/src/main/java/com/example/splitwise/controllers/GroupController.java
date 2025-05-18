package com.example.splitwise.controllers;

import com.example.splitwise.dtos.*;
import com.example.splitwise.services.GroupService;
import org.springframework.stereotype.Controller;

@Controller
public class GroupController {
    private GroupService service;

    public GroupController(GroupService service) {
        this.service = service;
    }

    public CreateGroupResponseDto createGroup(CreateGroupRequestDto requestDto) {
        CreateGroupResponseDto response = new CreateGroupResponseDto();
        try {
            response.setResponseStatus(ResponseStatus.SUCCESS);
            response.setGroup(service.createGroup(requestDto.getName(), requestDto.getDescription(), requestDto.getCreatorUserId()));
        } catch (Exception e) {
            response.setResponseStatus(ResponseStatus.FAILURE);
        }
        return response;
    }

    public DeleteGroupResponseDto deleteGroup(DeleteGroupRequestDto requestDto) {
        DeleteGroupResponseDto responseDto = new DeleteGroupResponseDto();
        try {
            service.deleteGroup(requestDto.getGroupId(), requestDto.getUserId());
            responseDto.setResponseStatus(ResponseStatus.SUCCESS);
        } catch (Exception e) {
            responseDto.setResponseStatus(ResponseStatus.FAILURE);
        }
        return responseDto;
    }
}
