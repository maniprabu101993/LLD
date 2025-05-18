package com.example.scaler.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class Batch extends BaseModel{

    private String name;

    private Schedule schedule;

}
