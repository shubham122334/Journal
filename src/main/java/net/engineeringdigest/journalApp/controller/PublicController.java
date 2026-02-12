package net.engineeringdigest.journalApp.controller;

import lombok.RequiredArgsConstructor;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/public")
public class PublicController {

    private final UserService userService;

    @PostMapping("create-user")
    public ResponseEntity<User> addUser(@RequestBody User entry) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(entry));
    }

}
