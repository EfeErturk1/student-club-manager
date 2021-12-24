package com.example.cs319project.service;

import com.example.cs319project.dto.AdvisorDto;
import com.example.cs319project.model.Admin;
import com.example.cs319project.model.Advisor;
import com.example.cs319project.model.Club;
import com.example.cs319project.model.Student;

import java.util.List;

public interface AdminService {
    Admin createNewAdmin(Admin admin);
}

