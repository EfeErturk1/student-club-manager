package com.example.cs319project.service.impl;

import com.example.cs319project.model.Club;
import com.example.cs319project.model.Student;
import com.example.cs319project.repository.ClubRepository;
import com.example.cs319project.service.ClubService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ClubServiceImpl implements ClubService {

    private final ClubRepository repository;

    @Override
    public Club findById(Integer id) {
       return repository.findClubById(id);
    }

    @Override
    public Club findByMembers(List<Student> students) {
        return repository.findClubByClubsMembers(students);
    }
}
