package com.fwishtter.userservice.service;

import com.fwishtter.auth.UserLoginRequest;
import com.fwishtter.auth.UserRegisterRequest;
import com.fwishtter.common.BaseResponse;
import org.springframework.web.multipart.MultipartFile;

public interface AuthService {
    BaseResponse register(UserRegisterRequest userRegisterRequest);

    BaseResponse login(UserLoginRequest userLoginRequest);

    void logout();
}
