package com.example.ProjectShoes.dto.response;

import com.example.ProjectShoes.common.Gender;
import com.example.ProjectShoes.entity.Address;
import com.example.ProjectShoes.entity.Role;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponse {
    private Long id;
    private String userName;
    private String name;
    private String email;
    private String phoneNumber;
    private Gender gender;
    private AddressResponse address;
}

