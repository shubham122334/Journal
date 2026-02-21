package net.engineeringdigest.journalApp.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.RequiredArgsConstructor;
import net.engineeringdigest.journalApp.cache.AppCache;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.services.RedisService;
import net.engineeringdigest.journalApp.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RedisService redisService;

    @GetMapping("/all-user")
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> users=redisService.get("admin-all-user", new TypeReference<List<User>>() {});
        if(users!=null){
            return ResponseEntity.ok(users);
        }
        List<User> allUsers=userService.getAllUsers();
        redisService.set("admin-all-user",allUsers,300L);
     return ResponseEntity.ok(allUsers);
    }

    @PostMapping("/create-admin")
    public ResponseEntity<User> addUser(@RequestBody User user){
        return ResponseEntity.ok(userService.createAdmin(user));
    }
}
