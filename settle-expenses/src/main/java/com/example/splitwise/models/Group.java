package com.example.splitwise.models;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Group extends BaseModel{
    private String name;
    private String description;
    @OneToMany(fetch = FetchType.EAGER)
    private List<User> users;
    @OneToMany(fetch = FetchType.EAGER)
    private List<User> admins;
}
