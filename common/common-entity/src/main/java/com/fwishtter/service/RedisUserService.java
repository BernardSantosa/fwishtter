package com.fwishtter.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fwishtter.constant.RedisConstant;
import com.fwishtter.helper.ObjectHelper;
import com.fwishtter.user.GetUserResponseDto;
import com.fwishtter.user.SimpleUserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
@RequiredArgsConstructor
public class RedisUserService {

    private final RedisService redisService;
    private static final String userService = "USER_SERVICE";
    private static final String apiProfile = "PROFILE";
    private static final long redisTimeout = 30;
    private static final TimeUnit redisTimeUnit = TimeUnit.MINUTES;
    private final ObjectMapper objectMapper;

    public void cacheUserProfile(UUID userId, GetUserResponseDto getUserResponseDto) {
        String jsonSave;
        String keyJson = userService.concat("::").concat(apiProfile).concat("::").concat(String.valueOf(userId));
        try{
            redisService.remove(keyJson);
            jsonSave = ObjectHelper.objectWriter().writeValueAsString(getUserResponseDto);
            redisService.set(keyJson, jsonSave, redisTimeout, redisTimeUnit);
        } catch (Exception ex) {
            log.error("[RedisUserService] Error when converting json to string: {}", ex.getMessage());
        }
    }

    public void cacheAllActiveUser(List<SimpleUserDto> simpleUserDtoList) {
        try {
            String key = RedisConstant.ALL_ACTIVE_USER;
            redisService.remove(key);
            String jsonString = objectMapper.writeValueAsString(simpleUserDtoList);
            redisService.set(key, jsonString, 1, TimeUnit.DAYS);
        } catch (Exception ex) {
            log.error("[RedisUserService] Error when converting json to string: {}", ex.getMessage());
        }
    }

    public List<SimpleUserDto> getAllActiveUser() {
        String jsonBody = redisService.get(RedisConstant.ALL_ACTIVE_USER);
        if(jsonBody.isEmpty()) {
            return List.of();
        } else {
            try {
                return objectMapper.readValue(jsonBody, new TypeReference<List<SimpleUserDto>>() {
                });
            } catch (Exception ex) {
                return List.of();
            }
        }
    }

    public GetUserResponseDto getUserProfileCache(UUID userId) {
        String jsonBody = userService.concat("::").concat(apiProfile).concat("::").concat(String.valueOf(userId));
        String value = redisService.get(jsonBody);

        if(value.isEmpty()) {
            return null;
        } else {
            try {
                return ObjectHelper.objectMapper().readValue(value, GetUserResponseDto.class);
            } catch (Exception e) {
                return null;
            }
        }
    }

    public void deleteRedisKey(UUID userId) {
        try {
            String jsonObject = userService.concat("::").concat(apiProfile).concat("::").concat(String.valueOf(userId));
            redisService.remove(jsonObject);
        } catch (Exception e) {
            log.error("[RedisUserService] Error when deleting redis keys: {}", e.getMessage());
        }
    }
}
