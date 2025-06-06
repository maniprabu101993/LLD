package com.example.ecom.repositories;

import com.example.ecom.models.Inventory;
import com.example.ecom.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

}
