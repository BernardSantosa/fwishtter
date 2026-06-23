package com.fwishtter.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
@Builder
public class JwtQueryException extends RuntimeException {

    private Integer status;
    private HttpStatus code;
    private String message;
    private Object errors;

    @JsonIgnore
    @Override
    public StackTraceElement[] getStackTrace() { return super.getStackTrace(); }
}
