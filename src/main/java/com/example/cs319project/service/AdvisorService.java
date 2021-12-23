package com.example.cs319project.service;

import com.example.cs319project.dto.AdvisorDto;
import com.example.cs319project.model.Advisor;
import com.example.cs319project.model.Club;
import com.example.cs319project.model.Student;

import java.util.List;

public interface AdvisorService {
    List<Advisor> findAll();
    Advisor findById(Integer id);
    Advisor createNewAdvisor(Advisor advisor);
    Advisor findByClub(Club club);
}
