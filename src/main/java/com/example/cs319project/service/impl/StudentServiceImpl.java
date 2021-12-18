package com.example.cs319project.service.impl;

import com.example.cs319project.model.Student;
import com.example.cs319project.repository.StudentRepository;
import com.example.cs319project.service.StudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository repository;

    @Override
    public Student findById(Integer id) {
        return repository.findStudentById(id);
    }

    @Override
    public Student createNewStudent(Student student) {
        Objects.requireNonNull(student, "student cannot be null");
        return repository.save(student);
    }
}
