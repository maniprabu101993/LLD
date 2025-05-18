package com.assignment.adapters;

import com.assignment.libraries.paytm.PaytmApi;
import com.assignment.libraries.paytm.PaytmPaymentResponse;
import com.assignment.models.Payment;
import com.assignment.models.PaymentStatus;

public class PaytmAdapter implements PaymentGatewayAdapter {
    private PaytmApi api;

    public PaytmAdapter() {
        this.api = new PaytmApi();
    }

    @Override
    public Payment makePayment(long billId, double amount) {
        PaytmPaymentResponse response = api.makePayment(billId, amount);
        Payment res = new Payment();
        res.setPaymentStatus(PaymentStatus.valueOf(response.getPaymentStatus()));
        res.setBillId(billId);
        res.setTxnId(response.getTxnId());
        return res;
    }
}
