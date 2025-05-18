package com.example.ecom.controllers;

import com.example.ecom.dtos.DeliveryEstimateRequestDto;
import com.example.ecom.dtos.DeliveryEstimateResponseDto;
import com.example.ecom.dtos.ResponseStatus;
import com.example.ecom.services.ProductService;
import org.springframework.stereotype.Controller;

import java.util.Date;

@Controller
public class ProductController {

    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    public DeliveryEstimateResponseDto estimateDeliveryTime(DeliveryEstimateRequestDto requestDto){
        DeliveryEstimateResponseDto responseDto= new DeliveryEstimateResponseDto();
        try{
            Date estimateDate =  productService.estimateDeliveryDate(requestDto.getProductId(), requestDto.getAddressId());
            responseDto.setExpectedDeliveryDate(estimateDate);
            responseDto.setResponseStatus(ResponseStatus.SUCCESS);
        }catch(Exception e){
            responseDto.setResponseStatus(ResponseStatus.FAILURE);
        }

        return responseDto;
    }
}
