package com.example.cs319project.service.impl;

import com.example.cs319project.exception.UserNotFoundException;
import com.example.cs319project.model.Role;
import com.example.cs319project.model.User;
import com.example.cs319project.repository.UserRepository;
import com.example.cs319project.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void deleteUser(User user) {
        Objects.requireNonNull(user, "user cannot be null");
        userRepository.delete(user);
    }

    @Override
    public User createNewUser(User user) {
        Objects.requireNonNull(user, "user cannot be null");
        return userRepository.save(user);
    }

    @Override
    public boolean assignRoleToUser(User user, Role role) {
        Objects.requireNonNull(user, "user cannot be null");
        Objects.requireNonNull(role, "role cannot be null");
        user.setRole(role);
        return false;
    }

    @Override
    public User searchUsersByName(String name) {
        Optional<User> users = userRepository.findUserByName(name);
        if(!users.isPresent()){
            LOGGER.info("there is no one at that name");
            return null;
        }
        return users.get();
    }

    @Override
    public User findById(Integer id) {
        Optional<User> users = userRepository.findById(id);
        if(users.isPresent()){
            LOGGER.info("there is a user with that id");
            return users.get();
        }
        LOGGER.info("There is no user with that id");
        return null;
    }

    @Override
    public User findByName(String username) {
        Objects.requireNonNull(username, "username cannot be null");
        return userRepository.findUserByName(username).orElseThrow(UserNotFoundException::new);
    }


    @Override
    public Boolean existsByName(String username) {
        Objects.requireNonNull(username, "username cannot be null");
        return userRepository.existsByName(username);
    }

    @Override
    public Boolean existsByEmail(String email) {
        Objects.requireNonNull(email, "email cannot be null");
        return userRepository.existsByEmail(email);
    }
}
