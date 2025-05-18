package com.scaler.repositories;

import com.scaler.models.DietaryRequirement;
import com.scaler.models.MenuItem;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MenuRepositoryImpl implements MenuRepository{
    private Map<Long, MenuItem> menuItems;
    private static long idCounter = 0;

    public MenuRepositoryImpl() {
        menuItems = new HashMap<>();
    }

    @Override
    public MenuItem add(MenuItem menuItem) {
        if(menuItem.getId() == 0){
            menuItem.setId(++idCounter);
        }
        menuItems.put(menuItem.getId(), menuItem);
        return menuItem;
    }

    @Override
    public List<MenuItem> getAll() {
        return menuItems.values().stream().collect(Collectors.toList());
    }

    @Override
    public List<MenuItem> getByDietaryRequirement(DietaryRequirement dietaryRequirement) {
        return menuItems.values().stream().filter(menuItem -> menuItem.getDietaryRequirement() == dietaryRequirement).collect(Collectors.toList());
    }
}
