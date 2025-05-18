package com.example.scaler.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import lombok.Data;


@Entity
@Data
public class User extends BaseModel{

    private String name;
    private String email;
    private UserType userType;
}
