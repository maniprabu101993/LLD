package com.example.scaler.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.util.Date;

@Data
@Entity
public class LearnerExam extends BaseModel{
    @ManyToOne
    private Learner learner;
    @ManyToOne
    private Exam exam;

    private Date startedAt;

    private Date endedAt;

    private ExamStatus status;

    private int scoreObtained;
}
