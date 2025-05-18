package com.example.scaler.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
@Entity
public class Batch extends BaseModel {

    private String name;
    @Enumerated
    private Schedule schedule;

}
