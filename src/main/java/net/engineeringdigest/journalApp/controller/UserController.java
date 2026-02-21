package net.engineeringdigest.journalApp.controller;

import lombok.RequiredArgsConstructor;
import net.engineeringdigest.journalApp.cache.AppCache;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.services.UserService;
import net.engineeringdigest.journalApp.services.WeatherService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;



@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final WeatherService weatherService;
    public final AppCache appCache;

    @PutMapping
    public ResponseEntity<Void> updateUser(@RequestBody User user) {
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        String username=authentication.getName();
        User userInDb=userService.getUserByUsername(username);
        if(userInDb!=null){
            userInDb.setUsername(user.getUsername());
            userInDb.setPassword(user.getPassword());
            userService.updateUser(userInDb);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("{city}")
    public ResponseEntity<String> getUser(@PathVariable String city){
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        String username=authentication.getName();
        String message="Hi "+username+", weather feel like:"+weatherService.getApiResponse(city).getCurrent().getWeatherDescriptions();
        return new ResponseEntity<>("Hi "+message, HttpStatus.OK);
    }

    @GetMapping("/clear-cache")
    public ResponseEntity<Void> clearCache(){
        appCache.initCache();
        return ResponseEntity.ok().build();
    }


}
