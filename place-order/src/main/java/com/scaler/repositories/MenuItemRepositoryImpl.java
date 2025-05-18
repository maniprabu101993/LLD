package com.scaler.repositories;

import com.scaler.models.MenuItem;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class MenuItemRepositoryImpl implements MenuItemRepository {

    Map<Long, MenuItem> map = new HashMap<>();
    int id = 1;

    @Override
    public MenuItem add(MenuItem menuItem) {
        if (menuItem.getId() == 0) {
            menuItem.setId(id++);
        }
        map.put(menuItem.getId(), menuItem);
        return menuItem;
    }

    @Override
    public Optional<MenuItem> findById(long id) {
        return Optional.of(map.get(id));
    }
}
