package com.microservices.userservice.controller;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.microservices.userservice.dto.BaseResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class ErrorController {
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<BaseResponse<String>> apiExecption(
            ResponseStatusException exception
    ) {
        return ResponseEntity.status(exception.getStatusCode()).body(BaseResponse.<String>builder()
                .message(exception.getReason()).build());

    }
}
