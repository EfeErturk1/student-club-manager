package com.example.cs319project.service;


import com.example.cs319project.model.Role;
import com.example.cs319project.model.RoleType;

public interface RoleService {
    Role findByName(RoleType name);
}
