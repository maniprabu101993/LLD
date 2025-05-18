package com.example.cloudproviders.services;

import com.example.cloudproviders.adapters.CloudAdapter;
import com.example.cloudproviders.exceptions.UserNotFoundException;
import com.example.cloudproviders.models.Connection;
import com.example.cloudproviders.repositories.UserRepository;
import java.util.Optional;
import com.example.cloudproviders.models.User;

public class CloudServiceImpl implements CloudService {
    private UserRepository repo;
    private CloudAdapter adapter;
    public CloudServiceImpl(UserRepository repo,CloudAdapter adapter){
        this.repo=repo;
        this.adapter=adapter;
    }
    public Connection createConnection(Long userId) throws UserNotFoundException{
        Optional<User> user= repo.findUserById(userId);
        if(user.isEmpty()){
            throw new UserNotFoundException(userId+ "not found");
        }
        return adapter.createConnection(userId);
    }
}
