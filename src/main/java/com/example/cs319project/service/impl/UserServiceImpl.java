package com.example.cs319project.service.impl;

import com.example.cs319project.model.User;
import com.example.cs319project.repository.UserRepository;
import com.example.cs319project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    @Override
    public Boolean existsByName(String username) {
        return repository.existsUserByUsername(username);
    }

    @Override
    public Boolean existsByEmail(String email) {
        return repository.existsUserByEmail(email);
    }

    @Override
    public User createNewUser(User user) {
        Objects.requireNonNull(user, "user cannot be null");
        return repository.save(user);
    }

    @Override
    public User findByEmail(String email) {
        return repository.findByEmail(email);
    }
}
