package com.example.cs319project.service.impl;


import com.example.cs319project.model.Advisor;
import com.example.cs319project.model.Club;
import com.example.cs319project.model.Event;
import com.example.cs319project.repository.AdvisorRepository;
import com.example.cs319project.service.AdvisorService;
import com.example.cs319project.service.StudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Service
public class AdvisorServiceImpl implements AdvisorService {

    private final AdvisorRepository repository;

    @Override
    public List<Advisor> findAll(){
        return repository.findAll();
    }

    @Override
    public Advisor findById(Integer id) {
        System.out.println(id);
        if(repository.findAdvisorById(id) == null)
            System.out.println('s');
        return repository.findAdvisorById(id);
    }

    @Override
    public Advisor createNewAdvisor(Advisor advisor) {
        Objects.requireNonNull(advisor, "advisor cannot be null");
        return repository.save(advisor);
    }

    @Override
    public Advisor findByClub(Club club) {
        if(repository.findAdvisorByClub(club) == null){
            System.out.println("club has no advisor");
            return null;
        }
        return repository.findAdvisorByClub(club);
    }
}

