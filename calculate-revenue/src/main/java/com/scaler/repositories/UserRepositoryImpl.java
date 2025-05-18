package com.scaler.repositories;

import com.scaler.models.User;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class UserRepositoryImpl implements UserRepository {
    Map<Long, User> map = new HashMap<>();
    int id = 0;

    public Optional<User> findById(long id) {
        return Optional.ofNullable(map.get(id));
    }

    public User save(User user) {
        if (user.getId() == 0) {
            user.setId(++id);
        }
        map.put(user.getId(), user);
        return user;
    }
}
