package com.fwishtter.userservice.security;

import com.fwishtter.security.JwtInterceptor;
import com.fwishtter.security.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.UUID;

@Component
@Slf4j
@RequiredArgsConstructor
public class SecurityInterceptor implements HandlerInterceptor {

    private final JwtService jwtService;
    private final JwtInterceptor jwtInterceptor;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            log.warn("Request to {} rejected: Header Authorization Empty!", request.getRequestURI());
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Please Login First!");
        }

        String token = authHeader.substring(7);

        if (!jwtService.isTokenValid(token)) {
            log.error("Request to {} rejected: Token Not valid!", request.getRequestURI());
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token already expired or not exist!");
        }

        String username = jwtService.extractUsername(token);
        jwtInterceptor.setJwt(token);
        jwtInterceptor.setUserName(username);
        jwtInterceptor.setTraceId(UUID.randomUUID().toString());
        log.info("Request Authorized for user: {}", username);

        return true;
    }

}
