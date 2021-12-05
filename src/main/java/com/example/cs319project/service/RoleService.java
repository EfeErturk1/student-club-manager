package com.example.cs319project.service;

import com.example.cs319project.model.Role;

public interface RoleService {
    Role findByName(String name);
}