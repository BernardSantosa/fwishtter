package com.microservices.userservice.service;

import com.fwishtter.entity.user.User;
import com.microservices.userservice.dto.*;
import com.microservices.userservice.exception.BaseException;
import com.microservices.userservice.mapper.UserMapper;
import com.microservices.userservice.repository.UserRepository;
import com.microservices.userservice.security.JwtInterceptor;
import com.microservices.userservice.security.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import tools.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImp implements AuthService {

    private final JwtService jwtService;
    private final JwtInterceptor jwtInterceptor;
    private final UserRepository userRepository;
    private final ObjectMapper mapper = new ObjectMapper();
    private final KafkaTemplate<String, String> kafkaTemplate;
    private static final String TOPIC = "user-registration-event";
    private final ObjectMapper objectMapper;

    @Transactional
    @Override
    public BaseResponse register(UserRegisterRequest userRegisterRequest) {
        String password;

        if(userRegisterRequest.getPassword().equals(userRegisterRequest.getReTypePassword())) {
            password = userRegisterRequest.getPassword();
        } else {
            log.error(String.format("%s - Registration failed for user %s: re-type password fields do not match.", "Authentication Service", mapper.writeValueAsString(userRegisterRequest)));
            Map<String, String> errors = new HashMap<>();
            errors.put("re_type_password", "The re-type password fields do not match.");
            throw new BaseException(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST, "re-type password do not match", errors);
        }

        log.info("Find if Username and Email Already in DB");

        userRepository.findUserByEmail(userRegisterRequest.getEmail())
                .ifPresent(user -> {
                    log.error("Email Already Used!");
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email Exist!");
                });

        userRepository.findUserByPhoneNumber(userRegisterRequest.getPhoneNumber())
                .ifPresent(user -> {
                    log.error("Phone Number Already Used!");
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Phone Number Already Used!");
                });

        userRepository.findUserByHandle(userRegisterRequest.getHandle())
                .ifPresent(user -> {
                    log.error("Handle Already Used!");
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Handle Already Used!");
                });


        User newUser = UserMapper.mapRegisterRequestToUser(userRegisterRequest);

        userRepository.saveAndFlush(newUser);

        Object response = UserMapper.mapToUserRegisterResponse(newUser);

        return new BaseResponse(
                HttpStatus.CREATED.value(),
                HttpStatus.CREATED,
                "User Registered Successfully!",
                response
        );
    }

    @Override
    public BaseResponse login(UserLoginRequest userLoginRequest) {

        User user = userRepository.findUserByEmail(userLoginRequest.getEmail())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED,  "Username Wrong!"));

        if(BCrypt.checkpw(userLoginRequest.getPassword(), user.getPassword())) {
            Map<String, Object> extraClaims = new HashMap<>();
            extraClaims.put("userId", user.getId());

            user.setToken(jwtService.generateToken(extraClaims, user));
            user.setExpired_at(System.currentTimeMillis() + 1000L * 60 * 60 * 24 * 30);
            userRepository.save(user);
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Password Wrong!");
        }

        Object response = UserMapper.mapToUserLoginResponse(user);

        return new BaseResponse<>(
                HttpStatus.OK.value(),
                HttpStatus.OK,
                "Login Successfully, Welcome!",
                response
        );
    }

    @Override
    public void logout() {
        String currentToken = jwtInterceptor.getJwt();

        User user = userRepository.findByToken(currentToken)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token Invalid"));

        user.setToken(null);
        user.setExpired_at(null);
        userRepository.save(user);
    }
}
