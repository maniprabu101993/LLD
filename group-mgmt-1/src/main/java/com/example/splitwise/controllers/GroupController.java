package com.example.splitwise.controllers;

import com.example.splitwise.dtos.*;
import com.example.splitwise.services.GroupService;
import org.springframework.stereotype.Controller;

@Controller
public class GroupController {
    GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    public AddMemberResponseDto addMember(AddMemberRequestDto requestDto) {
        AddMemberResponseDto response = new AddMemberResponseDto();
        try {
            response.setResponseStatus(ResponseStatus.SUCCESS);
            response.setGroupMember(groupService.addMember(requestDto.getGroupId(), requestDto.getAdminId(), requestDto.getMemberId()));
        } catch (Exception e) {
            response.setResponseStatus(ResponseStatus.FAILURE);
        }
        return response;
    }

    public RemoveMemberResponseDto removeMember(RemoveMemberRequestDto requestDto) {
        RemoveMemberResponseDto response = new RemoveMemberResponseDto();
        try {
            groupService.removeMember(requestDto.getGroupId(), requestDto.getAdminId(), requestDto.getMemberId());
            response.setResponseStatus(ResponseStatus.SUCCESS);
        } catch (Exception e) {
            response.setResponseStatus(ResponseStatus.FAILURE);
        }
        return response;
    }

    public FetchMembersResponseDto fetchMembers(FetchMembersRequestDto requestDto) {
        FetchMembersResponseDto response = new FetchMembersResponseDto();
        try {
                response.setResponseStatus(ResponseStatus.SUCCESS);
                response.setMembers(groupService.fetchAllMembers(requestDto.getGroupId(), requestDto.getMemberId()));
        } catch (Exception e) {
            response.setResponseStatus(ResponseStatus.FAILURE);
        }
        return response;
    }
}
