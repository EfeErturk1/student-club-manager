package com.example.cs319project.repository;

import com.example.cs319project.model.Event;
import com.example.cs319project.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface StudentRepository extends JpaRepository<Student, Integer> {
    Student findStudentById(Integer id);
    Set<Student> findAllByJoinedEventsContaining(Event event);
}
