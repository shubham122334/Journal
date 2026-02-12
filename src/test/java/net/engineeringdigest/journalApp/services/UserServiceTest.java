package net.engineeringdigest.journalApp.services;


import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {

    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        userService = new UserService(passwordEncoder,userRepository);
    }

    @Test
    void getUserByUsername() {
        User username=userService.getUserByUsername("Ram");
        assertNotNull(username);
        assertEquals("Ram",username.getUsername());
    }

    @ParameterizedTest
    @CsvSource({
            "1,2,3",
            "2,2,4",
           "1,31,32"
    })
    void add(int a , int b , int expected){
        assertEquals(expected,a+b);
    }

    @Test
    void getAllUsers() {
        assertNotNull(userService.getAllUsers());
    }
}