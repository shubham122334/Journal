package net.engineeringdigest.journalApp.services;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;


@Service
@RequiredArgsConstructor
public class RedisService {

    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper;

    public <T> T get(String key, Class<T> clazz) {
        try {
            String value = redisTemplate.opsForValue().get(key);

            if (value == null) {
                return null;
            }

            return objectMapper.readValue(value, clazz);

        } catch (Exception e) {
            throw new RuntimeException("Error fetching data from Redis", e);
        }
    }

    public <T> T get(String key, TypeReference<T> typeReference) {
        try {
            String value = redisTemplate.opsForValue().get(key);

            if (value == null) {
                return null;
            }

            return objectMapper.readValue(value, typeReference);

        } catch (Exception e) {
            throw new RuntimeException("Error fetching data from Redis", e);
        }
    }



    public void set(String key, Object value, Long ttl) {
        try {
            String json = objectMapper.writeValueAsString(value);

            if (ttl != null && ttl > 0) {
                redisTemplate.opsForValue().set(key, json, ttl, TimeUnit.SECONDS);
            } else {
                redisTemplate.opsForValue().set(key, json);
            }

        } catch (Exception e) {
            throw new RuntimeException("Error saving data to Redis", e);
        }
    }

    public void delete(String key) {
        redisTemplate.delete(key);
    }
}