package com.fwishtter.security;

import com.fwishtter.common.BaseResponse;
import com.fwishtter.model.JwtQueryException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@Component
public class AuthEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        Throwable throwable = (Throwable) request.getAttribute("exception");

        BaseResponse body;
        if(throwable instanceof JwtQueryException jwtException) {
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setStatus(jwtException.getStatus());
            Map<String, String> customMessage = new LinkedHashMap<>();
            customMessage.put("status_message", "JWT_QUERY_VALIDATION_ERROR");

            body = BaseResponse.builder()
                    .status(jwtException.getStatus())
                    .code(jwtException.getCode())
                    .message("Jwt Query Validation Error")
                    .data(customMessage)
                    .build();
        } else {
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            Map<String, String> customMessage = new LinkedHashMap<>();
            customMessage.put("status_message", "JWT_QUERY_VALIDATION_ERROR");

            body = BaseResponse.builder()
                    .status(HttpStatus.UNAUTHORIZED.value())
                    .code(HttpStatus.UNAUTHORIZED)
                    .message("Access denied. Please provide valid credentials.")
                    .data(customMessage)
                    .build();
        }

        final ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(), body);
    }

}
