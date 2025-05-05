package com.example.ProjectShoes.dto.request;

import com.example.ProjectShoes.common.Gender;
import com.example.ProjectShoes.common.UserStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UserRequest {

    private String userName;
    @Size(min = 6 ,message = "Password must be at least 6 characters")
    private String password;
    private String name;
    @Temporal(TemporalType.DATE)
    private Date birthday;
    private String email;
    private String phoneNumber;
    private Gender gender;
    private String avatar;

    @Enumerated(value = EnumType.STRING)
    private UserStatus status;

    private AddressRequest address;
    private Long roleId;
}
