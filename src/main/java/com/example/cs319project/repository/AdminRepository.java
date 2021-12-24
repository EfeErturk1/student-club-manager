package com.example.cs319project.repository;

import com.example.cs319project.model.Admin;
import com.example.cs319project.model.Advisor;
import com.example.cs319project.model.Club;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Integer> {

}