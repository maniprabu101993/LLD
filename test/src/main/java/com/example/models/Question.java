package com.example.scaler.models;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Question extends BaseModel{
    private String name;
    private String description;
    @ManyToOne
    private Exam exam;
    @OneToMany(fetch = FetchType.EAGER)
    private List<Option> options;
    @ManyToOne
    private Option correctOption;
    private int score;
}
