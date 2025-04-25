package com.example.ProjectShoes.controller;

import com.example.ProjectShoes.entity.Role;
import com.example.ProjectShoes.service.RoleService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/role")
@Slf4j(topic = "ROLE-CONTROLLER")
@Tag(name = "ROLE-CONTROLLER")
public class RoleController {

    private final RoleService roleService;

    @PostMapping("/create")
    public Role createRole(@RequestBody Role role) {
        return roleService.createRole(role);
    }
}
