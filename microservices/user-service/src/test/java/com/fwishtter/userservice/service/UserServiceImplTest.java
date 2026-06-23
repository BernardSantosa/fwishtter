package com.fwishtter.userservice.service;

import com.fwishtter.auth.UserRegisterRequest;
import com.fwishtter.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import tools.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthServiceImp authServiceImp;

    @Mock
    private UserRegisterRequest userRegisterRequest;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private KafkaTemplate kafkaTemplate;

    @Test
    void registerShouldBeSuccess() {
        UserRegisterRequest user = new UserRegisterRequest();
        user.setPassword("pass123");
        user.setReTypePassword("pass123");

        authServiceImp.register(user);
        System.out.println("My first unit test");
    }

}