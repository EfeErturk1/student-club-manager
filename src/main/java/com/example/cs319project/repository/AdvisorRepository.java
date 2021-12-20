package com.example.cs319project.repository;

import com.example.cs319project.model.Advisor;
import com.example.cs319project.model.Club;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdvisorRepository extends JpaRepository<Advisor, Integer> {
    Advisor findAdvisorById(Integer id);
    Advisor findAdvisorByClub(Club club);
}
