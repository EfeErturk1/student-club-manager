package com.example.cs319project.service;

import com.example.cs319project.dto.ClubDto;
import com.example.cs319project.model.Club;
import com.example.cs319project.model.Student;

import java.util.List;
import java.util.Set;

public interface ClubService {
    Club findById(Integer id);
    Club createNewClub(Club club);
    void deleteClub(Club club);
    List<Club> findAll();
    void updateClub(ClubDto dto);
    Club saveClub(Club club);
}
