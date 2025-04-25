package com.example.ProjectShoes.service.impl;

import com.example.ProjectShoes.entity.Role;
import com.example.ProjectShoes.repository.RoleRepository;
import com.example.ProjectShoes.service.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "ROLE-SERVICE")
public class RoleImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public Role createRole(Role request) {
        Role role = new Role();
        role.setName(request.getName());
        role.setDescription(request.getDescription());

        return roleRepository.save(role);
    }
}
