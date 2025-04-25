package com.example.ProjectShoes.dto.request;

import com.example.ProjectShoes.common.UserRole;
import com.example.ProjectShoes.entity.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UserCreatedRequest {

    private String userName;
    private String password;
    private String name;
    @Temporal(TemporalType.DATE)
    private Date birthday;
    private String email;
    private String phoneNumber;
    private String gender;
    private String avatar;
    private Boolean status;
    private AddressCreatedRequest address;
    private Role role;
    private UserRole userRole;
}
