package com.example.splitwise.models;

import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
public class Group extends BaseModel{
    private String name;
    private String description;
}
