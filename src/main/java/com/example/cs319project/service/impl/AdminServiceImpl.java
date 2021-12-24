package com.example.cs319project.service.impl;


import com.example.cs319project.dto.AdvisorDto;
import com.example.cs319project.dto.ClubDto;
import com.example.cs319project.model.Admin;
import com.example.cs319project.model.Advisor;
import com.example.cs319project.model.Club;
import com.example.cs319project.model.Event;
import com.example.cs319project.repository.AdminRepository;
import com.example.cs319project.repository.AdvisorRepository;
import com.example.cs319project.service.AdminService;
import com.example.cs319project.service.AdvisorService;
import com.example.cs319project.service.StudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Service
public class AdminServiceImpl implements AdminService {

    private final AdminRepository repository;

    @Override
    public Admin createNewAdmin(Admin admin) {
        Objects.requireNonNull(admin, "advisor cannot be null");
        return repository.save(admin);
    }

}
