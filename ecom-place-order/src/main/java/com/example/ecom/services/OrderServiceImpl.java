package com.example.ecom.services;

import com.example.ecom.exceptions.*;
import com.example.ecom.models.*;
import com.example.ecom.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class OrderServiceImpl implements OrderService{
    private UserRepository userRepository;
    private AddressRepository addressRepository;
    private ProductRepository productRepository;
    private OrderRepository orderRepository;
    private OrderDetailRepository orderDetailRepository;
    private HighDemandProductRepository highDemandProductRepository;
    private InventoryRepository inventoryRepository;

    @Autowired
    public OrderServiceImpl(UserRepository userRepository, AddressRepository addressRepository, ProductRepository productRepository, OrderRepository orderRepository, OrderDetailRepository orderDetailRepository, HighDemandProductRepository highDemandProductRepository, InventoryRepository inventoryRepository) {
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
        this.highDemandProductRepository = highDemandProductRepository;
        this.inventoryRepository = inventoryRepository;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Override
    public Order placeOrder(int userId, int addressId, List<Pair<Integer, Integer>> orderDetails) throws UserNotFoundException, InvalidAddressException, OutOfStockException, InvalidProductException, HighDemandProductException {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));
        Address adress = addressRepository.findById(addressId).orElseThrow(() -> new InvalidAddressException("Invalid Address"));
        if (adress.getUser().getId() != userId) {
            throw new InvalidAddressException("Address not belong to user");
        }
        List<OrderDetail> lists=new ArrayList<>();
        for (Pair<Integer, Integer> pai : orderDetails) {
            Inventory inventory = inventoryRepository.findByProductId(pai.getFirst()).orElseThrow(() -> new OutOfStockException("Product Not found in inventory"));
            if (inventory.getQuantity() < pai.getSecond()) {
                throw new OutOfStockException("Product out of stock");
            }
            inventory.setQuantity(inventory.getQuantity() - pai.getSecond());
            inventoryRepository.save(inventory);

            Optional<HighDemandProduct> high= highDemandProductRepository.findByProductId(pai.getFirst());
            if(high.isPresent()){
                if(high.get().getMaxQuantity()<pai.getSecond()){
                    throw new HighDemandProductException("Product is in high demand, you can order only limited quantity");
                }
            }
            OrderDetail detail=new OrderDetail();
            detail.setQuantity(pai.getSecond());
            detail.setProduct(productRepository.findById(pai.getFirst()).orElseThrow(() -> new InvalidProductException("Product not found")));
            orderDetailRepository.save(detail);
            lists.add(detail);
        }

        Order order = new Order();
        order.setUser(user);
        order.setDeliveryAddress(adress);
        order.setOrderDetails(lists);
        order.setOrderStatus(OrderStatus.PLACED);
        return orderRepository.save(order);

    }
}
