package com.example.cloudproviders.controllers;

import com.example.cloudproviders.dtos.CreateConnectionRequestDto;
import com.example.cloudproviders.dtos.CreateConnectionResponseDto;
import com.example.cloudproviders.dtos.ResponseStatus;
import com.example.cloudproviders.services.CloudService;
import com.example.cloudproviders.models.ConnectionStatus;
import com.example.cloudproviders.models.Connection;
public class CloudController {
    private CloudService cloudService;

    public CloudController(CloudService cloudService) {
        this.cloudService = cloudService;
    }

    public CreateConnectionResponseDto createConnection(CreateConnectionRequestDto createConnectionRequestDto) {
        CreateConnectionResponseDto response=new CreateConnectionResponseDto();
        try{
            Connection conn =  cloudService.createConnection(createConnectionRequestDto.getUserId());
            response.setConnectionId(conn.getConnectionId());
            response.setResponseStatus(ResponseStatus.SUCCESS);
            response.setConnectionStatus(conn.getConnectionStatus());
        }catch(Exception e){
            response.setResponseStatus(ResponseStatus.FAILURE);
        }
        return response;
    }
}
