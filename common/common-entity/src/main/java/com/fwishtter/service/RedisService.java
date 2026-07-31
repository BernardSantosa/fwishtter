package com.fwishtter.service;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisService {

    private final RedisTemplate<String, String> redisTemplate;

    public RedisService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void set(String key, String value, long timeout, TimeUnit unit) {
        redisTemplate.opsForValue().set(key, value);
        redisTemplate.expire(key, timeout, unit);
    }

    public void set(String key, String value) { redisTemplate.opsForValue().set(key, value); }

    public String get(String key) { return redisTemplate.opsForValue().get(key); }

    public String remove(String key) { return redisTemplate.opsForValue().getAndDelete(key); }

    public String update(String key, String value) { return redisTemplate.opsForValue().getAndSet(key, value); }
}
