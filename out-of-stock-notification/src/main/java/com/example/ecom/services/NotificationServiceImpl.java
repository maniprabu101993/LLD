package com.example.ecom.services;

import com.example.ecom.exceptions.*;
import com.example.ecom.libraries.NotificationAdapter;
import com.example.ecom.models.*;
import com.example.ecom.repositories.InventoryRepository;
import com.example.ecom.repositories.NotificationRepository;
import com.example.ecom.repositories.ProductRepository;
import com.example.ecom.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NotificationServiceImpl implements NotificationService{
    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Notification registerUser(int userId, int productId) throws UserNotFoundException, ProductNotFoundException, ProductInStockException {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));
        Product product = productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException("Product not found"));
        Inventory inventory =  null;
        Optional<Inventory> inventoryOptional = this.inventoryRepository.findByProduct(product);
        if(inventoryOptional.isEmpty()){
            throw new ProductNotFoundException("Product not found!!");
        }

        if(inventoryOptional.get().getQuantity()>0){
            throw new ProductInStockException("Product is in stock..!");
        }
        Notification notification =new Notification();
        notification.setUser(user);
        notification.setProduct(product);
        notification.setStatus(NotificationStatus.PENDING);
        notificationRepository.save(notification);
        return notification;
    }

    @Override
    public void deregisterUser(int userId, int notificationId) throws UserNotFoundException, NotificationNotFoundException, UnAuthorizedException {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));
        Notification notification = null;
        List<Notification> notifications = notificationRepository.findAll();
        for(Notification not: notifications){
            if(not.getId()== notificationId){
                notification= not;
            }
        }
        if(notification==null){
            throw new NotificationNotFoundException("Notification not found");
        }
        if( notification.getUser() !=null){
            if(notification.getUser().getId()==userId){
                notificationRepository.deleteById(notification.getId());
            }else{
                throw new UnAuthorizedException("User not found!!");
            }
        }
    }
}
