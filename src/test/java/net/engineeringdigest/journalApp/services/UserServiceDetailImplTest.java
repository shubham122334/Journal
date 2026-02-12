package net.engineeringdigest.journalApp.services;

import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

//@ExtendWith(MockitoExtension.class)
class UserServiceDetailImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceDetailImpl userServiceDetail;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void loadUserByUsername() {
        User user=new User();
        user.setUsername("Ram");
        user.setPassword("ram123");
        user.setRoles(new ArrayList<>());

        when(userRepository.findByUsername("Ram")).thenReturn(user);
        UserDetails fetchedUser=userServiceDetail.loadUserByUsername("Ram");
        assertNotNull(fetchedUser);
        assertEquals("Ram",fetchedUser.getUsername());
        assertEquals("ram123",fetchedUser.getPassword());
    }

    @Test
    void loadUserByUsername_Not_Found() {

        when(userRepository.findByUsername("Ram")).thenReturn(null);

        assertThrows(UsernameNotFoundException.class,()->userServiceDetail.loadUserByUsername("Ram"));
        verify(userRepository,times(1)).findByUsername("Ram");

    }
}