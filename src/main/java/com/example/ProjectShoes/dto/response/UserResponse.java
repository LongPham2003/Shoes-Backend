package com.example.ProjectShoes.dto.response;

import com.example.ProjectShoes.common.Gender;
import com.example.ProjectShoes.entity.Address;
import com.example.ProjectShoes.entity.Role;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponse {
    private Long id;
    private String userName;
    private String name;
    @Temporal(TemporalType.DATE)
    private Date birthday;
    private String email;
    private String phoneNumber;
    private Gender gender;
    private AddressResponse address;
}

