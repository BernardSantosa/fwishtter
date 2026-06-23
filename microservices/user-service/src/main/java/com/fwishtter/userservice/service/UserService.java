package com.fwishtter.userservice.service;

import com.fwishtter.common.BaseResponse;
import com.fwishtter.user.UpdateUserRequestDto;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {

    BaseResponse uploadPhoto(MultipartFile photoFile);

    BaseResponse getUserDetailByDisplayName();

    BaseResponse updateUserDetail(UpdateUserRequestDto userData);
}
