package com.example.cs319project.service;

import com.example.cs319project.dto.ClubDto;
import com.example.cs319project.model.Event;
import com.example.cs319project.model.Student;
import com.example.cs319project.model.request.StudentResponse;

import java.util.List;
import java.util.Set;

public interface StudentService {
    Student findById(Integer id);
    Student createNewStudent(Student student);
    Set<Student> findAllStudentRegisteredEvent(Event event);
    Student saveorUpdateStudent(Student student);
    void deleteStudent(Student student);
    void updateStudent(StudentResponse dto);
    List<Student> findAll();
}
