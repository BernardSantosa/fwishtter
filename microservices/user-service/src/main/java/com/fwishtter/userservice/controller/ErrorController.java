package com.fwishtter.userservice.controller;

import com.fwishtter.common.BaseResponse;
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
