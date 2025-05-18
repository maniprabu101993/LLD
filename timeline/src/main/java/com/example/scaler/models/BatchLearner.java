package com.example.scaler.models;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class BatchLearner extends BaseModel{
    @ManyToOne
    public Batch batch;
    @ManyToOne
    public Learner learner;
    public Date entryDate;
    public Date exitDate;
}
