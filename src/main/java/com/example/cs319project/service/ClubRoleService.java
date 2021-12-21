package com.example.cs319project.service;

import com.example.cs319project.model.Club;
import com.example.cs319project.model.clubstrategy.ClubRole;

import java.util.List;

public interface ClubRoleService {
    List<ClubRole> findByClubId(Integer id);
    List<ClubRole> findByStudentId(Integer id);
    ClubRole assignNewRole(ClubRole role);
    void deleteRole(ClubRole role);
}
