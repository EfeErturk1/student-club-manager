package com.example.cs319project.service;

import com.example.cs319project.model.Student;

public interface StudentService {
    Student findById(Integer id);
    Student createNewStudent(Student student);
}
