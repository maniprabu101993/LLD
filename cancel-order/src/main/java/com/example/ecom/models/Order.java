package com.example.ecom.models;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class Order extends BaseModel{
    @OneToOne
    private User user;
    @OneToMany
    private List<OrderDetail> orderDetails;
    private OrderStatus orderStatus;
}
