package com.example.cs319project.service.impl;

import com.example.cs319project.model.Role;
import com.example.cs319project.repository.RoleRepository;
import com.example.cs319project.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

    private RoleRepository repository;

    @Autowired
    public RoleServiceImpl(RoleRepository repo){
        this.repository = repo;
    }

    @Override
    public Role findByName(String name) {
        return repository.findByName(name);
    }
}
