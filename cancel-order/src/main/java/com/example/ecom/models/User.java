package com.example.ecom.models;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@Entity
public class User extends BaseModel{
    private String name;
    private String email;
    @OneToMany
    private List<Order> orders;
}
