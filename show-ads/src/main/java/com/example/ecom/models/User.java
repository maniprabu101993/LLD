package com.example.ecom.models;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class User extends BaseModel{
    private String name;
    private String email;
    @OneToMany(fetch = FetchType.EAGER)
    private List<Preference> preferences;
}
