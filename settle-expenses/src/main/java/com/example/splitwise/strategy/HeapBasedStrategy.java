package com.example.splitwise.strategy;

import com.example.splitwise.models.Transaction;
import com.example.splitwise.models.User;
import com.example.splitwise.services.Pair;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class HeapBasedStrategy implements SettleUpStrategy{
    @Override
    public List<Transaction> settleUpUser(Map<User, Double> map) {
        PriorityQueue<Pair> receivers = new PriorityQueue<>(Collections.reverseOrder());
        PriorityQueue<Pair> givers = new PriorityQueue<>();

        List<Transaction> transactions = new ArrayList<>();

        for(User user: map.keySet()){
            Double amount = map.get(user);
            if(amount<0){
                givers.add(new Pair(user,-1.0*amount));
            }
            else if(amount>0){
                receivers.add(new Pair(user,amount));
            }
        }

        while(receivers.size()>0 && givers.size()>0){
            Pair giver = givers.poll();
            Pair receiver = receivers.poll();
            Transaction tr= new Transaction();
            tr.setAmount(giver.getAmount());
            tr.setPaidTo(receiver.getUser());
            tr.setPaidFrom(giver.getUser());
            transactions.add(tr);

            if(giver.getAmount() < receiver.getAmount()){
                receivers.add(new Pair(receiver.getUser(),receiver.getAmount()- giver.getAmount()));
            }
        }
        return transactions;
    }
    }
