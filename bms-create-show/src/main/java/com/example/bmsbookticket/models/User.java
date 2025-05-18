package com.example.bmsbookticket.models;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
@Entity
public class User extends BaseModel{

    private String name;
    private String email;
    private String password;
    @Enumerated(EnumType.ORDINAL)
    private UserType userType;
}
