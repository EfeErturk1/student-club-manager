package com.example.cs319project.service;

import com.example.cs319project.model.Club;
import com.example.cs319project.model.Student;

import java.util.List;
import java.util.Set;

public interface ClubService {
    Club findById(Integer id);
    Club findByMembers(Set<Student> students);
    Club createNewClub(Club club);
}
