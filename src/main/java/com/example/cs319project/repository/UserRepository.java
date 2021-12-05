package com.example.cs319project.repository;

import com.example.cs319project.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {
    public User findUserByUsername(String username);
    public boolean existsUserByUsername(String name);
    public boolean existsUserByEmail(String email);
    public User findByEmail(String email);

}
