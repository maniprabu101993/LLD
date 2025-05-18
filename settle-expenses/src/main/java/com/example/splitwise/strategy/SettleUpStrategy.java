package com.example.splitwise.strategy;

import com.example.splitwise.models.Transaction;
import com.example.splitwise.models.User;

import java.util.List;
import java.util.Map;

public interface SettleUpStrategy {

    List<Transaction> settleUpUser(Map<User, Double> map);
}
