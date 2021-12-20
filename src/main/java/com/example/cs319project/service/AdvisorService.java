package com.example.cs319project.service;

import com.example.cs319project.model.Advisor;
import com.example.cs319project.model.Club;
import com.example.cs319project.model.Student;

public interface AdvisorService {
    Advisor findById(Integer id);
    Advisor createNewAdvisor(Advisor advisor);
    Advisor findByClub(Club club);

}
