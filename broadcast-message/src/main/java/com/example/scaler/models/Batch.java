package com.example.scaler.models;

import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
public class Batch extends BaseModel{

    private String name;
}
