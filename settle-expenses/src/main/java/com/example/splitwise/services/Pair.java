package com.example.splitwise.services;

import com.example.splitwise.models.User;

public class Pair implements Comparable<Pair> {
    private User user;
    private double amount;

    public Pair(User user, double amount) {
        this.user = user;
        this.amount = amount;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public int compareTo(Pair o) {
        if (this.amount > o.amount) {
            return 1;
        } else if (this.amount < o.amount) {
            return -1;
        }
        return 0;
    }
}
