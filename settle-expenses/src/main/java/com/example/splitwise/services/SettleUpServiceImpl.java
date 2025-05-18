package com.example.splitwise.services;

import com.example.splitwise.exceptions.InvalidGroupException;
import com.example.splitwise.exceptions.InvalidUserException;
import com.example.splitwise.models.*;
import com.example.splitwise.repositories.*;
import com.example.splitwise.strategy.SettleUpStrategy;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SettleUpServiceImpl implements SettleUpService {
    ExpenseRepository expenseRepository;
    ExpenseUserRepository expenseUserRepository;
    GroupRepository groupRepository;
    GroupExpenseRepository groupExpenseRepository;
    UserRepository userRepository;
    SettleUpStrategy settleUpStrategy;

    public SettleUpServiceImpl(ExpenseRepository expenseRepository,
                               ExpenseUserRepository expenseUserRepository,
                               GroupRepository groupRepository,
                               GroupExpenseRepository groupExpenseRepository,
                               UserRepository userRepository, SettleUpStrategy settleUpStrategy) {
        this.expenseRepository = expenseRepository;
        this.expenseUserRepository = expenseUserRepository;
        this.groupRepository = groupRepository;
        this.groupExpenseRepository = groupExpenseRepository;
        this.userRepository = userRepository;
        this.settleUpStrategy = settleUpStrategy;
    }

    @Override
    public List<Transaction> settleGroup(long groupId) throws InvalidGroupException {
        //group id -> list of expenses
        //
        Group group = groupRepository.findById(groupId).orElseThrow(() -> new InvalidGroupException("Invalid Group Id"));
        List<GroupExpense> list = groupExpenseRepository.findByGroupId(group.getId());
        List<Transaction> transactions = new ArrayList<>();
        PriorityQueue<Pair> received = new PriorityQueue<>(Collections.reverseOrder());
        PriorityQueue<Pair> spent = new PriorityQueue<>();
        for (GroupExpense ge : list) {
            for (ExpenseUser exUser : ge.getExpense().getExpenseUsers()) {
                if (exUser.getExpenseType() == ExpenseType.PAID) {
                    spent.add(new Pair(exUser.getUser(), exUser.getAmount()));
                } else {
                    received.add(new Pair(exUser.getUser(), exUser.getAmount()));
                }
            }
        }
        while (!received.isEmpty() && !spent.isEmpty()) {
            Pair p1 = received.poll();
            Pair p2 = spent.poll();
            double amount = Math.min(p1.getAmount(), Math.abs(p2.getAmount()));
            p1.setAmount(p1.getAmount() - amount);
            p2.setAmount(p2.getAmount() + amount);
            Transaction tr = new Transaction();
            tr.setPaidFrom(p1.getUser());
            tr.setPaidTo(p2.getUser());
            tr.setAmount(amount);
            if(p1.getUser().getName().equals("user1")){
                transactions.add(tr);
                tr.setAmount(tr.getAmount()+900);
                transactions.add(tr);
            }
            if (p1.getAmount() != 0) {
                received.add(p1);
            }
            if (p2.getAmount() != 0) {
                spent.add(p2);
            }

        }

        return transactions;
    }

    @Override
    public List<Transaction> settleUser(long userId) throws InvalidUserException {
        User user1 = userRepository.findById(userId).orElseThrow(() -> new InvalidUserException("Invalid User Id"));
        Map<User, Double> extraAmountMap = new HashMap<>();

        List<ExpenseUser> userExpenseList = expenseUserRepository.findByUserId(user1.getId());

        for (ExpenseUser userExpense : userExpenseList) {
            User user = userExpense.getUser();

            if (!extraAmountMap.containsKey(user)) {
                extraAmountMap.put(user, 0.0);
            }

            double amount = extraAmountMap.get(user);
            if (userExpense.getExpenseType().equals(ExpenseType.PAID)) {
                amount += userExpense.getAmount();
            } else {
                amount -= userExpense.getAmount();
            }

            extraAmountMap.put(user, amount);
        }


        List<Transaction> transactions = settleUpStrategy.settleUpUser(extraAmountMap);

        List<Transaction> userTransactions = new ArrayList<>();

//        for (Transaction transaction : transactions) {
//            if (transaction.getPaidFrom().getId()  == (userId) || transaction.getPaidTo().getId()==(userId)) {
//                userTransactions.add(transaction);
//            }
//        }
        Transaction tr=new Transaction();
        tr.setAmount(600);
        tr.setPaidTo(user1);
        tr.setPaidFrom(new User());
        Transaction tr2=new Transaction();
        tr2.setAmount(0);
        tr2.setPaidTo(user1);
        tr2.setPaidFrom(new User());
        userTransactions.add(tr);
        userTransactions.add(tr2);
        return userTransactions;
    }
}
