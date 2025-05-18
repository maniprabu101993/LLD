package com.example.ecom.services;

import com.example.ecom.exceptions.ProductNotFoundException;
import com.example.ecom.libraries.NotificationAdapter;
import com.example.ecom.models.Inventory;
import com.example.ecom.models.Notification;
import com.example.ecom.models.NotificationStatus;
import com.example.ecom.models.Product;
import com.example.ecom.repositories.InventoryRepository;
import com.example.ecom.repositories.NotificationRepository;
import com.example.ecom.repositories.ProductRepository;
import com.example.ecom.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InventoryServiceImpl implements InventoryService {
    @Autowired
    private InventoryRepository inventoryRepository;
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private NotificationAdapter adapter;
    @Autowired
    private NotificationRepository notifyRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public Inventory updateInventory(int productId, int quantity) throws ProductNotFoundException {
        Product product = this.productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException("Product not found"));
        Optional<Inventory> inventoryOptional = this.inventoryRepository.findByProduct(product);
        Inventory inventory;
        if (inventoryOptional.isEmpty()) {
            inventory = new Inventory();
            inventory.setProduct(product);
            inventory.setQuantity(quantity);
            return this.inventoryRepository.save(inventory);
        }
        inventory = inventoryOptional.get();
        inventory.setQuantity(inventory.getQuantity() + quantity);
        List<Notification> list = notifyRepository.findAll();
        for (Notification not : list) {
            if(not.getProduct().getId() == productId){
                String message = product.getName() + " back in stock!";
                String body = "Dear "+ not.getUser().getName() +", "+ product.getName()+"is now back in stock. Grab it ASAP!";
                adapter.sendNotification(message, body);
                not.setStatus(NotificationStatus.SENT);
                notifyRepository.save(not);
            }
        }


        return this.inventoryRepository.save(inventory);
    }
}
