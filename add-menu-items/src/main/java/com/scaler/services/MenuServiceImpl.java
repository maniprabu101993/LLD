package com.scaler.services;

import com.scaler.exceptions.UnAuthorizedAccess;
import com.scaler.exceptions.UserNotFoundException;
import com.scaler.models.*;
import com.scaler.repositories.MenuRepository;
import com.scaler.repositories.UserRepository;
import com.scaler.repositories.UserRepositoryImpl;

import java.util.Optional;

public class MenuServiceImpl implements MenuService{

    private final MenuRepository menuRepository;
    private final UserRepository userRepository;

    public MenuServiceImpl(MenuRepository menuRepository, UserRepository userRepository) {
        this.menuRepository = menuRepository;
        this.userRepository = userRepository;
    }
    @Override
    public MenuItem addMenuItem(long userId, String name, double price, String dietaryRequirement, String itemType, String description) throws UserNotFoundException, UnAuthorizedAccess {
        Optional<User> userOptional = userRepository.findById(userId);
        if(userOptional.isEmpty()){
            throw new UserNotFoundException("User not found Exception");
        }
        User user = userOptional.get();
        if(user.getUserType() != UserType.ADMIN){
            throw new UnAuthorizedAccess("User is not an admin , try with different admin user");
        }
        MenuItem menuItem = new MenuItem();
        menuItem.setName(name);
        menuItem.setPrice(price);
        menuItem.setDietaryRequirement(DietaryRequirement.valueOf(dietaryRequirement));
        menuItem.setItemType(ItemType.valueOf(itemType));
        menuItem.setDescription(description);
        menuItem = menuRepository.add(menuItem);
        return menuItem;
    }
}
