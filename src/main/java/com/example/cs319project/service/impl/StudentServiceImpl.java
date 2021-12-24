package com.example.cs319project.service.impl;

import com.example.cs319project.model.Event;
import com.example.cs319project.model.Student;
import com.example.cs319project.repository.StudentRepository;
import com.example.cs319project.service.StudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository repository;

    @Override
    public void deleteStudent(Student student){
        repository.delete(student);
    }

    @Override
    public Student findById(Integer id) {
        System.out.println(id);
        if(repository.findStudentById(id) == null)
            System.out.println('s');
        return repository.findStudentById(id);
    }

    @Override
    public Student createNewStudent(Student student) {
        Objects.requireNonNull(student, "student cannot be null");
        return repository.save(student);
    }

    @Override
    public Set<Student> findAllStudentRegisteredEvent(Event event) {
        return repository.findAllByJoinedEventsContaining(event);
    }

    @Override
    public Student saveorUpdateStudent(Student student) {
        return repository.save(student);
    }
}
