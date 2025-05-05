package com.example.ProjectShoes.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class AddressResponse {

    private String province;

    private String district;

    private String ward;

    private String specificAddress;

}
