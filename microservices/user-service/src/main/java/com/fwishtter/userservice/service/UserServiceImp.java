package com.fwishtter.userservice.service;

import com.fwishtter.common.BaseResponse;
import com.fwishtter.entity.user.User;
import com.fwishtter.mapper.UserMapper;
import com.fwishtter.model.BaseException;
import com.fwishtter.repository.UserRepository;
import com.fwishtter.security.JwtInterceptor;
import com.fwishtter.service.RedisUserService;
import com.fwishtter.user.UpdateUserRequestDto;
import com.fwishtter.user.GetUserResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImp implements UserService {

    private final JwtInterceptor jwtInterceptor;
    private final UserRepository userRepository;
    private final RedisUserService redisUserService;
    private final String uploadDir = "uploads/profiles";

    @Transactional
    @Override
    public BaseResponse uploadPhoto(MultipartFile photoFile) {

        log.info("JWT USERNAME: [{}]", jwtInterceptor.getUserName());
        User user = userRepository.findUserByHandle(jwtInterceptor.getUserName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND ,"User Not Found"));

        if(photoFile.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Photo file is empty");
        }

        String originalFileName = photoFile.getOriginalFilename();
        String extension = originalFileName.substring(originalFileName.lastIndexOf(".")).toLowerCase();

        if (!(extension.equals(".jpg") ||
                extension.equals(".jpeg") ||
                extension.equals(".png") ||
                extension.equals(".webp"))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid image extension");
        }

        try {
            String fileName = user.getHandle() + '_' + System.currentTimeMillis() + "_" + photoFile.getOriginalFilename();
            Path targetPath = Paths.get(uploadDir).toAbsolutePath().normalize().resolve(fileName);

            Files.createDirectories(targetPath.getParent());
            Files.copy(photoFile.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);

            String profileUrl = "/uploads/profiles/" + fileName;
            user.setProfilePicture(profileUrl);
            userRepository.save(user);

            return new BaseResponse(
                    HttpStatus.OK.value(),
                    HttpStatus.OK,
                    "profile photo updated!",
                    profileUrl
            );

        } catch (IOException e) {
            log.error("failed to store file: ", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed Upload Photo!");
        }
    }

    @Override
    public BaseResponse getProfileByDisplayName() {

        Optional<User> optionalUser = userRepository.findUserByHandle(jwtInterceptor.getUserName());

        if(optionalUser.isPresent()) {
            User user = optionalUser.get();

            GetUserResponseDto response = UserMapper.mapToUserResponseDto(user);

            return BaseResponse.builder()
                    .status(HttpStatus.OK.value())
                    .code(HttpStatus.OK)
                    .message("your detail has been provided!")
                    .data(response)
                    .build();
        }

        return BaseResponse.builder()
                .status(HttpStatus.NO_CONTENT.value())
                .code(HttpStatus.NO_CONTENT)
                .message("failed to provide your detail")
                .data(null)
                .build();
    }

    public BaseResponse getUserDetail(UUID userId) {

        Optional<User> userOptional = userRepository.findUserById(userId);
        if(userOptional.isPresent()) {
            User user = userOptional.get();
            GetUserResponseDto response = UserMapper.mapToUserResponseDto(user);

            return BaseResponse.builder()
                    .status(HttpStatus.OK.value())
                    .code(HttpStatus.OK)
                    .message("user detail has been provided!")
                    .data(response)
                    .build();
        }

        return BaseResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .code(HttpStatus.BAD_REQUEST)
                .message("failed to get detail user detail!")
                .data(null)
                .build();
    }

    @Override
    public BaseResponse getUserList() {

        List<User> userList = userRepository.findAll();
        if(userList.isEmpty()) {
            return BaseResponse.builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .code(HttpStatus.BAD_REQUEST)
                    .message("no user exist")
                    .data(null)
                    .build();
        }

        List<GetUserResponseDto> dataUsers = new ArrayList<>();

        userList.forEach(data -> {

            GetUserResponseDto responseDto = UserMapper.mapToUserResponseDto(data);
            dataUsers.add(responseDto);
            redisUserService.cacheUserProfile(data.getId(), responseDto);
        });

        return BaseResponse.builder()
                .status(HttpStatus.OK.value())
                .code(HttpStatus.OK)
                .message("user list provided")
                .data(dataUsers)
                .build();
    }

    @Transactional
    @Override
    public BaseResponse updateUserDetail(UpdateUserRequestDto userData) {

        Optional<User> optionalUser = userRepository.findUserByHandle(jwtInterceptor.getUserName());

        if(optionalUser.isPresent()) {

            boolean isHandleExist = userRepository.existsByHandle(userData.getHandle());

            if(isHandleExist) {
                throw new BaseException(
                        HttpStatus.CONFLICT.value(),
                        HttpStatus.CONFLICT,
                        "handle already used \uD83E\uDEE0\u200B",
                        null
                );
            }

            User user = optionalUser.get();

            user.setDisplayName(userData.getDisplayName());
            user.setHandle(user.getHandle());
            user.setBio(userData.getBio());
            user.setAddress(userData.getAddress());

            userRepository.save(user);

            return BaseResponse.builder()
                    .status(HttpStatus.OK.value())
                    .code(HttpStatus.OK)
                    .message("user data updated Successfully!")
                    .data(null)
                    .build();
        }

        return BaseResponse.builder()
                .status(HttpStatus.NO_CONTENT.value())
                .code(HttpStatus.NO_CONTENT)
                .message("failed to update user data")
                .data(null)
                .build();
    }


}
