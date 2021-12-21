package com.example.cs319project.service.impl;

import com.example.cs319project.model.Club;
import com.example.cs319project.model.clubstrategy.ClubRole;
import com.example.cs319project.repository.ClubRoleRepository;
import com.example.cs319project.service.ClubRoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Service
public class ClubRoleServiceImpl implements ClubRoleService {

    private final ClubRoleRepository repository;

    @Override
    public List<ClubRole> findByClubId(Integer id) {
        return repository.findByClubId(id);
    }

    @Override
    public List<ClubRole> findByStudentId(Integer id) {
        return repository.findByStudentId(id);
    }

    @Override
    public ClubRole assignNewRole(ClubRole role) {
        Objects.requireNonNull(role, "user cannot be null");
        return repository.save(role);
    }

    @Override
    public void deleteRole(ClubRole role){
        repository.delete(role);
    }
}
