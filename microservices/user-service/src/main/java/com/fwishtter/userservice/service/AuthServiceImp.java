package com.fwishtter.userservice.service;

import com.fwishtter.auth.UserLoginRequest;
import com.fwishtter.auth.UserRegisterRequest;
import com.fwishtter.common.BaseResponse;
import com.fwishtter.entity.user.User;
import com.fwishtter.mapper.UserMapper;
import com.fwishtter.model.BaseException;
import com.fwishtter.repository.UserRepository;
import com.fwishtter.security.JwtInterceptor;
import com.fwishtter.security.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;

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
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

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

        String encodedPassword = passwordEncoder.encode(password);
        String finalHandle = generateUniqueHandle(userRegisterRequest.getHandle());
        User newUser = UserMapper.mapRegisterRequestToUser(userRegisterRequest, encodedPassword, finalHandle);

        userRepository.saveAndFlush(newUser);

        Object response = UserMapper.mapToUserRegisterResponse(newUser);

        return new BaseResponse(
                HttpStatus.CREATED.value(),
                HttpStatus.CREATED,
                "User Registered Successfully!",
                response
        );
    }

    public String generateUniqueHandle(String baseHandle) {
        String cleanHandle = baseHandle.replaceAll("\\s+", "").toLowerCase();
        String candidate = cleanHandle;
        int counter = 1;
        int maxAttempts = 10;

        while (userRepository.existsByHandle(candidate) && counter <= maxAttempts) {
            candidate = cleanHandle + counter;
            counter++;
        }

        if (counter > maxAttempts) {
            candidate = cleanHandle + UUID.randomUUID().toString().substring(0, 5);
        }

        return candidate;
    }

    @Override
    public BaseResponse login(UserLoginRequest userLoginRequest) {

        User user = userRepository.findUserByEmail(userLoginRequest.getEmail())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED,  "Email / Password Wrong!"));

        assert user.getPassword() != null;
        if(BCrypt.checkpw(userLoginRequest.getPassword(), user.getPassword())) {
            Map<String, Object> extraClaims = new HashMap<>();
            extraClaims.put("userId", user.getId());

            String jwtToken = jwtService.generateToken(extraClaims, user);
            Long expiredAt = System.currentTimeMillis() + 1000L * 60 * 60 * 24 * 30;
//            user.setToken(jwtService.generateToken(extraClaims, user));
//            user.setExpired_at(System.currentTimeMillis() + 1000L * 60 * 60 * 24 * 30);
//            userRepository.save(user);

            Object response = UserMapper.mapToUserLoginResponse(user, jwtToken, expiredAt);

            return new BaseResponse<>(
                    HttpStatus.OK.value(),
                    HttpStatus.OK,
                    "Login Successfully, Welcome!",
                    response
            );
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Email / Password Wrong!");
        }
    }

    @Override
    public void logout() {
//        String currentToken = jwtInterceptor.getJwt();

//        User user = userRepository.findByToken(currentToken)
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token Invalid"));

//        user.setToken(null);
//        user.setExpired_at(null);
//        userRepository.save(user);
    }

}
