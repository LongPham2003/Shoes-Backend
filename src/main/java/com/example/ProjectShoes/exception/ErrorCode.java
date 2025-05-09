package com.example.ProjectShoes.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    USER_EXISTED( 400 ,"User existed",HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED( 400 ,"User not existed",HttpStatus.BAD_REQUEST),
    EMAIL_EXISTED( 400 ,"Email existed",HttpStatus.BAD_REQUEST),
    PHONE_NUMBER_EXISTED( 400 ,"Phone number existed",HttpStatus.BAD_REQUEST),
    ;

    private final int code;
    private final String message;
    private final HttpStatus statusCode;

}
