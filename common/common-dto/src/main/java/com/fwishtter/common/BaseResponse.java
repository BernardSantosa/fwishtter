package com.fwishtter.userservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public record BaseResponse <T>(
        Integer status,
        @JsonIgnore
        HttpStatus code,
        String message,
        T data
) implements Serializable {
}
