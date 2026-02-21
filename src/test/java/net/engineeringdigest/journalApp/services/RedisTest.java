package net.engineeringdigest.journalApp.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class RedisTest {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    void testRedis(){
        //redisTemplate.opsForValue().set("email","shubham1999.iimt@gmail.com");
        assertEquals("shubham1999.iimt@gmail.com", redisTemplate.opsForValue().get("email"));
    }
}
