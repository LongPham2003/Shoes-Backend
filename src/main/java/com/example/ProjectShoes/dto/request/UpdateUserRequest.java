package com.example.ProjectShoes.dto.request;

import com.example.ProjectShoes.common.Gender;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class UpdateUserRequest {
    private String name;
    @Temporal(TemporalType.DATE)
    private Date birthday;
    private String email;
    private String phoneNumber;
    private Gender gender;
    private String avatar;
}
