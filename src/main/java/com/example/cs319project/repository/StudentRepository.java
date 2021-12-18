package com.example.cs319project.repository;

import com.example.cs319project.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Integer> {
    Student findStudentById(Integer id);

}
