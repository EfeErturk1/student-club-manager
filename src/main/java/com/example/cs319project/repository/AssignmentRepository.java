package com.example.cs319project.repository;

import com.example.cs319project.model.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssignmentRepository extends JpaRepository<Assignment, Integer> {
    Assignment findByAssignmentId(Integer id);
    List<Assignment> findAll();
}
