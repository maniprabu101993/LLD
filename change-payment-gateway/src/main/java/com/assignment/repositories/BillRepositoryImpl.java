package com.assignment.repositories;

import com.assignment.models.Bill;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class BillRepositoryImpl implements BillRepository{
    private Map<Long,Bill> map=new HashMap<Long, Bill>();

    public Bill save(Bill bill){
        map.put(bill.getId(),bill);
        return bill;
    }

    public Optional<Bill> findById(long billId){
        return Optional.ofNullable(map.get(billId));
    }
}