package com.assignment.adapters;

import com.assignment.libraries.razorpay.RazorpayApi;
import com.assignment.libraries.razorpay.RazorpayPaymentResponse;
import com.assignment.models.Payment;
import com.assignment.models.PaymentStatus;

public class RazorPayAdapter implements PaymentGatewayAdapter {
    private RazorpayApi api;

    public RazorPayAdapter() {
        api = new RazorpayApi();
    }

    @Override
    public Payment makePayment(long billId, double amount) {
        RazorpayPaymentResponse response = api.processPayment(billId, amount);
        Payment res = new Payment();
        res.setPaymentStatus(PaymentStatus.valueOf(response.getPaymentStatus()));
        res.setBillId(billId);
        res.setTxnId(response.getTransactionId());
        return res;
    }
}
