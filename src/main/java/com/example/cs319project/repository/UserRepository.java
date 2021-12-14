package com.example.cs319project.repository;

import com.example.cs319project.model.Role;
import com.example.cs319project.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    List<User> findUserByRole(Role role);
    Optional<User> findUserByName(String name);
    User findUserByNameAndPassword(String name, String password);
    Boolean existsByName(String name);
    Boolean existsByEmail(String email);
}
