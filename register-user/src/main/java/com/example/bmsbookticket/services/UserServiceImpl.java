package com.example.bmsbookticket.services;

import com.example.bmsbookticket.models.User;
import com.example.bmsbookticket.repositories.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository repo;
    BCryptPasswordEncoder encrypt = new BCryptPasswordEncoder();

    public UserServiceImpl(UserRepository repo) {
        this.repo = repo;
    }

    @Override
    public User signupUser(String name, String email, String password) throws Exception {
        Optional<User> opt = repo.findByEmail(email);
        if (opt.isPresent()) {
            throw new Exception("User already exists with name: " + name);
        }
        User user = new User();
        user.setEmail(email);
        user.setName(name);

        user.setPassword(encrypt.encode(password));
        User temp = repo.save(user);
        User returnedUser = new User();
        returnedUser.setEmail(email);
        returnedUser.setName(name);
        returnedUser.setId(temp.getId());
        return returnedUser;
    }

    @Override
    public boolean login(String email, String password) throws Exception {
        Optional<User> opt = repo.findByEmail(email);
        if (opt.isEmpty()) {
            throw new Exception("User does not register: ");
        }
        User user = opt.get();
        return encrypt.matches(password, user.getPassword());
    }
}
