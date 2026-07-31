package com.fwishtter.userservice.controller;

import com.fwishtter.common.BaseResponse;
import com.fwishtter.user.UpdateUserRequestDto;
import com.fwishtter.userservice.service.UserService;
import com.fwishtter.userservice.service.UserServiceImp;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/upload-profile")
    public ResponseEntity<BaseResponse> uploadProfile(
            @RequestParam("file") MultipartFile photoFile
            ) {
        return ResponseEntity.ok(userService.uploadPhoto(photoFile));
    }

    @GetMapping
    public ResponseEntity<BaseResponse> getUserProfile() {
        BaseResponse response = userService.getProfileByDisplayName();
        return new ResponseEntity<>(response, response.code());
    }

    @PostMapping("/detail")
    public ResponseEntity<BaseResponse> getUserDetail(@Valid @RequestParam(name = "user_id") UUID userId) {
        BaseResponse response = userService.getUserDetail(userId);
        return new ResponseEntity<>(response, response.code());
    }

    @GetMapping("/list")
    public ResponseEntity<BaseResponse> getUserList() {
        BaseResponse response = userService.getUserList();
        return new ResponseEntity<>(response, response.code());
    }

    @PutMapping
    public ResponseEntity<BaseResponse> updateUserDetail(@Valid @RequestBody UpdateUserRequestDto userData) {
        BaseResponse response = userService.updateUserDetail(userData);
        return new ResponseEntity<>(response, response.code());
    }

}
