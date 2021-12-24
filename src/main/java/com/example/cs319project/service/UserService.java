package com.example.cs319project.service;

import com.example.cs319project.model.Role;
import com.example.cs319project.model.User;

public interface UserService {
    User createNewUser(User user);
    void deleteUser(User user);
    boolean assignRoleToUser(User user, Role role);
    User searchUsersByName(String name);
    User findById(Integer id);
    User findByName(String username);
    Boolean existsByName(String username);
    Boolean existsByEmail(String email);
}
