package net.engineeringdigest.journalApp.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class UserRepositoryImplTest {


    @Autowired
    private UserRepositoryImpl userRepositoryImpl;


    @BeforeEach
    void setUp() {

    }
    @Test
    void getUserForSA() {
        userRepositoryImpl.getUserForSA();
    }
}