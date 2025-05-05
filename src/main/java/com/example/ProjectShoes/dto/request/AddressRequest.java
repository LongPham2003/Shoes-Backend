package com.example.ProjectShoes.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressRequest {


    private String province;

    private String district;

    private String ward;

    private String specificAddress;

    private boolean defaultAddress;

    private Long user;
}
