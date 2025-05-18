package com.assignment.services;

import com.assignment.adapters.PaymentGatewayAdapter;
import com.assignment.exceptions.InvalidBillException;
import com.assignment.models.Bill;
import com.assignment.models.Payment;
import com.assignment.repositories.BillRepository;

import java.util.Optional;

public class PaymentServiceImpl implements PaymentService {
    private BillRepository repo;
    private PaymentGatewayAdapter adapter;

    public PaymentServiceImpl(BillRepository repo, PaymentGatewayAdapter adapter) {
        this.repo = repo;
        this.adapter = adapter;
    }

    public Payment makePayment(long billId) throws InvalidBillException {
        Optional<Bill> output = repo.findById(billId);
        if (!output.isPresent()) {
            throw new InvalidBillException("Invalid bill id " + billId);
        }
        double total = output.get().getTotalAmount();
        return adapter.makePayment(billId, total);

    }
}