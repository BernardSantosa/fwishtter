package com.microservices.userservice.service;

import com.microservices.userservice.dto.*;

public interface AuthService {
    BaseResponse register(UserRegisterRequest userRegisterRequest);

    BaseResponse login(UserLoginRequest userLoginRequest);

    void logout();
}
