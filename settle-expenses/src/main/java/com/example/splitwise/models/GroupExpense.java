package com.example.splitwise.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class GroupExpense extends BaseModel{
    @ManyToOne
    private Group group;
    @ManyToOne
    private Expense expense;
}
