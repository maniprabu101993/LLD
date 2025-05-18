package com.scaler.services;

import com.scaler.models.DietaryRequirement;
import com.scaler.models.MenuItem;
import com.scaler.repositories.MenuRepository;

import java.util.List;

public class MenuServiceImpl implements MenuService {
    private MenuRepository repo;

    public MenuServiceImpl(MenuRepository repo) {
        this.repo = repo;
    }

    public List<MenuItem> getMenuItems(String itemType) {
        if(itemType == null){
            return repo.getAll();
        } else{
            return repo.getByDietaryRequirement(DietaryRequirement.valueOf(itemType));
        }
    }
}
