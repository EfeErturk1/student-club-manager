package com.example.cs319project.repository;

import com.example.cs319project.model.Club;
import com.example.cs319project.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClubRepository extends JpaRepository<Club, Integer> {
    Club findClubById(Integer id);
    Club findClubByClubsMembers(List<Student> student);
}
