package com.example.ecom.controllers;

import com.example.ecom.dtos.CancelOrderRequestDto;
import com.example.ecom.dtos.CancelOrderResponseDto;
import com.example.ecom.dtos.ResponseStatus;
import com.example.ecom.models.Inventory;
import com.example.ecom.models.Order;
import com.example.ecom.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class OrderController {
    @Autowired
    private OrderService orderService;

    public CancelOrderResponseDto cancelOrder(CancelOrderRequestDto cancelOrderRequestDto) {
        CancelOrderResponseDto response = new CancelOrderResponseDto();
        try {
            Order order = orderService.cancelOrder(cancelOrderRequestDto.getOrderId(), cancelOrderRequestDto.getUserId());
            response.setOrder(order);
            response.setStatus(ResponseStatus.SUCCESS);
            return response;
        } catch (Exception e) {
            response.setStatus(ResponseStatus.FAILURE);
            return response;
        }
    }

}
