package com.example.cloudproviders.repositories;

import com.example.cloudproviders.models.User;

import java.util.Optional;
import java.util.Map;
import java.util.HashMap;

public class UserRepoImpl implements UserRepository {
    private Map<Long,User> map=new HashMap();
    public User save(User user){
        map.put(user.getId(),user);
        return user;
    }

    public Optional<User> findUserById(long userId){
        return Optional.of(map.get(userId));
    }
}
