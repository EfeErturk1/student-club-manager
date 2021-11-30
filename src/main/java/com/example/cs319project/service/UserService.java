package com.example.cs319project.service;

import com.example.cs319project.model.User;

public interface UserService {
    Boolean existsByName(String username);
    Boolean existsByEmail(String email);
    User createNewUser(User user);
    User findByEmail(String email);
}
