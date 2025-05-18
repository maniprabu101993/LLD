package com.assignment.controllers;

import com.assignment.dtos.MakePaymentRequestDto;
import com.assignment.dtos.MakePaymentResponseDto;
import com.assignment.dtos.ResponseStatus;
import com.assignment.models.Payment;
import com.assignment.services.PaymentService;

public class PaymentController {

    private PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    public MakePaymentResponseDto makePayment(MakePaymentRequestDto makePaymentRequestDto) {
         MakePaymentResponseDto response=new MakePaymentResponseDto();
         try{
            Payment pp= paymentService.makePayment(makePaymentRequestDto.getBillId());
            response.setResponseStatus(ResponseStatus.SUCCESS);
            response.setTxnId(pp.getTxnId());
            response.setPaymentStatus(pp.getPaymentStatus());
         }catch(Exception e){
             response.setResponseStatus(ResponseStatus.FAILURE);
         }
         
         return response;
    }
}