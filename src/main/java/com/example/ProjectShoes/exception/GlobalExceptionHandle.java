package com.example.ProjectShoes.exception;

import com.example.ProjectShoes.dto.response.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Slf4j(topic = "EXCEPTION-HANDLING")
@RestControllerAdvice
public class GlobalExceptionHandle {
    @ExceptionHandler(value = RuntimeException.class)
    ResponseEntity<ErrorResponse> handlingRuntimeException(RuntimeException exception, HttpServletRequest httpServletRequest) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(new Date())
                .status(HttpStatus.BAD_REQUEST.value())
                .error(INTERNAL_SERVER_ERROR.getReasonPhrase())
                .path(httpServletRequest.getRequestURI())
                .message(exception.getMessage())
                .build();

        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<ErrorResponse> validationHandling(MethodArgumentNotValidException exception, HttpServletRequest httpServletRequest) {

        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(new Date())
                .status(HttpStatus.BAD_REQUEST.value())
                .error(INTERNAL_SERVER_ERROR.getReasonPhrase())
                .path(httpServletRequest.getRequestURI())
                .message(exception.getFieldError().getDefaultMessage())
                .build();

        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ErrorResponse> AppExceptionHandling(AppException exception, HttpServletRequest httpServletRequest) {
        ErrorCode errorCode = exception.getErrorCode();
        log.info("Error code: {}",errorCode);
        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(new Date())
                .status(errorCode.getCode())
                .error(INTERNAL_SERVER_ERROR.getReasonPhrase())
                .path(httpServletRequest.getRequestURI())
                .message(exception.getMessage())
                .build();
        return ResponseEntity.status(errorCode.getStatusCode()).body(errorResponse);
    }
}
