package com.example.ecom.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Inventory extends BaseModel{
    @ManyToOne
    private Product product;
    private int quantity;
}
