package com.scaler.parking_lot.respositories;

import com.scaler.parking_lot.models.Invoice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InvoiceRepositoryImpl implements InvoiceRepository{
    private Map<Long, Invoice> map=new HashMap<>();
    @Override
    public Invoice save(Invoice invoice) {
        if (invoice.getId() == 0) {
            invoice.setId(map.size() + 1);
        }
        map.put(invoice.getId(), invoice);
        return invoice;
    }
}
