package net.engineeringdigest.journalApp.controller;

import lombok.RequiredArgsConstructor;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    @GetMapping("/all-user")
    public ResponseEntity<List<User>> getAllUsers(){
     return ResponseEntity.ok(userService.getAllUsers());
    }

    @PostMapping("/create-admin")
    public ResponseEntity<User> addUser(@RequestBody User user){
        return ResponseEntity.ok(userService.createAdmin(user));
    }
}
