package com.example.cs319project.service.impl;

import com.example.cs319project.exception.RoleNotFoundException;
import com.example.cs319project.model.Role;
import com.example.cs319project.model.RoleType;
import com.example.cs319project.repository.RoleRepository;
import com.example.cs319project.service.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public Role findByName(RoleType name) {
        Objects.requireNonNull(name, "role name cannot be null");
        return roleRepository.findRoleByName(name).orElseThrow(RoleNotFoundException::new);
    }
}
