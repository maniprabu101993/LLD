package com.scaler.controllers;

import com.scaler.dtos.*;
import com.scaler.models.MenuItem;
import com.scaler.services.MenuService;

import java.util.List;

public class MenuController {

    private MenuService menuService;

    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    public AddMenuItemResponseDto addMenuItem(AddMenuItemRequestDto requestDto){
        AddMenuItemResponseDto responseDto = new AddMenuItemResponseDto();
        try {
            MenuItem menuItems = menuService.addMenuItem(requestDto.getUserId(),requestDto.getName(),requestDto.getPrice(),
                    requestDto.getDietaryRequirement(),requestDto.getItemType(),requestDto.getDescription());
            responseDto.setMenuItem(menuItems);
            responseDto.setStatus(ResponseStatus.SUCCESS);
            return responseDto;
        } catch (Exception e){
            responseDto.setStatus(ResponseStatus.FAILURE);
            return responseDto;
        }
    }
}
