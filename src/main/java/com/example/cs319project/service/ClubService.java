package com.example.cs319project.service;

import com.example.cs319project.model.Club;
import com.example.cs319project.model.Student;

import java.util.List;

public interface ClubService {
    Club findById(Integer id);
    Club findByMembers(List<Student> students);
}
