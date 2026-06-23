package com.fwishtter.mapper;

import com.fwishtter.auth.UserLoginRequest;
import com.fwishtter.auth.UserLoginResponse;
import com.fwishtter.auth.UserRegisterRequest;
import com.fwishtter.auth.UserRegisterResponse;
import com.fwishtter.entity.user.User;
import com.fwishtter.user.GetUserResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class UserMapper {

    public static User mapRegisterRequestToUser(UserRegisterRequest userRegisterRequest, String encodedPassword,String finalHandle) {
        return User.builder()
                .displayName(userRegisterRequest.getDisplayName())
                .email(userRegisterRequest.getEmail())
                .password(encodedPassword)
                .handle(finalHandle)
                .phoneNumber(userRegisterRequest.getPhoneNumber())
                .bio(userRegisterRequest.getBio())
                .enabled(true)
                .profilePicture("default-avatar.png")
                .accountNonLocked(true)
                .accountNonExpired(true)
                .credentialsNonExpired(true)
                .build();
    }

    public static UserRegisterResponse mapToUserRegisterResponse (User user) {
        return UserRegisterResponse.builder()
                .username(user.getDisplayName())
                .email(user.getEmail())
                .build();
    }

    public static User mapLoginRequestToUser(UserLoginRequest userLoginRequest) {
        return  User.builder().build();
    }

    public static UserLoginResponse mapToUserLoginResponse (User user, String token, Long expiredAt) {
        return UserLoginResponse.builder()
                .username(user.getDisplayName())
                .token(token)
                .expired_at(expiredAt)
                .build();
    }

    public static GetUserResponseDto mapToUserResponseDto(User user) {
        return GetUserResponseDto.builder()
                .userId(user.getId())
                .displayName(user.getDisplayName())
                .email(user.getEmail())
                .handle(user.getHandle())
                .phoneNumber(user.getPhoneNumber())
                .bio(user.getBio())
                .profilePicture(user.getProfilePicture())
                .build();
    }
}
