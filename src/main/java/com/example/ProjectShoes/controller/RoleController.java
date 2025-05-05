package com.example.ProjectShoes.controller;

import com.example.ProjectShoes.entity.Role;
import com.example.ProjectShoes.service.RoleService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/role")
@Slf4j(topic = "ROLE-CONTROLLER")
@Tag(name = "ROLE-CONTROLLER")
public class RoleController {

    private final RoleService roleService;

    @PostMapping("/create")
    public Role createRole(@RequestBody Role role) {
        log.info("create role successfully");
        return roleService.createRole(role);
    }

    @GetMapping("/{id}")
    public Role getRoleByid(@PathVariable Long id){
        log.info("get role successfully");
        return roleService.findById(id);
    }
}
