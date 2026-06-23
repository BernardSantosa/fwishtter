package com.fwishtter.userservice.service;

import com.fwishtter.common.BaseResponse;
import com.fwishtter.entity.user.User;
import com.fwishtter.mapper.UserMapper;
import com.fwishtter.repository.UserRepository;
import com.fwishtter.security.JwtInterceptor;
import com.fwishtter.user.UpdateUserRequestDto;
import com.fwishtter.user.GetUserResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImp implements UserService {

    private final JwtInterceptor jwtInterceptor;
    private final UserRepository userRepository;
    private final String uploadDir = "uploads/profiles";

    @Transactional
    @Override
    public BaseResponse uploadPhoto(MultipartFile photoFile) {

        log.info("JWT USERNAME: [{}]", jwtInterceptor.getUserName());
        User user = userRepository.findUserByDisplayName(jwtInterceptor.getUserName())
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
                    "Profile photo Updated!",
                    profileUrl
            );

        } catch (IOException e) {
            log.error("Failed to store file: ", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed Upload Photo!");
        }
    }

    @Override
    public BaseResponse getUserDetailByDisplayName() {

        Optional<User> optionalUser = userRepository.findUserByDisplayName(jwtInterceptor.getUserName());

        if(optionalUser.isPresent()) {
            User user = optionalUser.get();

            GetUserResponseDto response = UserMapper.mapToUserResponseDto(user);

            return BaseResponse.builder()
                    .status(HttpStatus.OK.value())
                    .code(HttpStatus.OK)
                    .message("User Detail Has Been Provided!")
                    .data(response)
                    .build();
        }

        return BaseResponse.builder()
                .status(HttpStatus.NO_CONTENT.value())
                .code(HttpStatus.NO_CONTENT)
                .message("failed to proved user detail")
                .data(null)
                .build();
    }

    @Transactional
    @Override
    public BaseResponse updateUserDetail(UpdateUserRequestDto userData) {

        Optional<User> optionalUser = userRepository.findUserByDisplayName(jwtInterceptor.getUserName());

        if(optionalUser.isPresent()) {
            User user = optionalUser.get();

            user.setDisplayName(userData.getDisplayName());
            user.setHandle(user.getHandle());
            user.setBio(userData.getBio());
            user.setAddress(userData.getAddress());

            userRepository.save(user);

            return BaseResponse.builder()
                    .status(HttpStatus.OK.value())
                    .code(HttpStatus.OK)
                    .message("User Data Updated Successfully!")
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
