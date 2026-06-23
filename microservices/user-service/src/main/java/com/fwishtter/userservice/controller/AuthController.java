package com.fwishtter.userservice.controller;

import com.fwishtter.auth.UserLoginRequest;
import com.fwishtter.auth.UserRegisterRequest;
import com.fwishtter.common.BaseResponse;
import com.fwishtter.userservice.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping(value = "/register"
//                consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<BaseResponse> register(@Valid @RequestBody UserRegisterRequest userRegisterRequest) {
        BaseResponse response = authService.register(userRegisterRequest);
        return new ResponseEntity<>(response, response.code());
    }

    @PostMapping("/login")
    public ResponseEntity<BaseResponse> login(@Valid @RequestBody UserLoginRequest userLoginRequest) {
        BaseResponse response = authService.login(userLoginRequest);
        return new ResponseEntity<>(response, response.code());
    }

    @PostMapping("/logout")
    public BaseResponse logout() {
        authService.logout();
        return new BaseResponse(
                HttpStatus.OK.value(),
                HttpStatus.OK,
                "User Logout Successfull",
                null
        );
    }

}
