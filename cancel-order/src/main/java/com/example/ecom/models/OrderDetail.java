package com.example.ecom.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class OrderDetail extends BaseModel{
    @ManyToOne
    private Order order;
    @ManyToOne
    private Product product;
    private int quantity;
}
