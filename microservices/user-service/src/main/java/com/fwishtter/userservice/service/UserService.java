package com.fwishtter.userservice.service;

import com.fwishtter.common.BaseResponse;
import com.fwishtter.user.GetUserResponseDto;
import com.fwishtter.user.UpdateUserRequestDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface UserService {

    BaseResponse uploadPhoto(MultipartFile photoFile);

    BaseResponse getProfileByDisplayName();

    BaseResponse updateUserDetail(UpdateUserRequestDto userData);

    BaseResponse getUserDetail(UUID userID);

    BaseResponse getUserList();
}
