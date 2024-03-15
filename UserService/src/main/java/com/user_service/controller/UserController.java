package com.user_service.controller;

import com.user_service.entity.User;
import com.user_service.service.serviceImp.UserServiceImp;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserServiceImp userServiceImp;
    Logger log = LoggerFactory.getLogger(UserController.class);

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {

        return ResponseEntity.status(HttpStatus.OK).body(userServiceImp.createUser(user));
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUser() {
        return ResponseEntity.ok(userServiceImp.getAllUser());

    }

    //circuit breaker as this method calls another services

    @GetMapping("/{userId}")
    @CircuitBreaker(name = "hotelRatingBreaker", fallbackMethod = "hotelRatingBreaker")
    public ResponseEntity<User> getUserById(@PathVariable int userId) {
        User user = userServiceImp.getUserById(userId);

        return ResponseEntity.ok(user);

    }

    //return type and parameter  of fallback and getUserById must be same
    private ResponseEntity<User> hotelRatingBreaker(int useId, Exception ex) {
       // System.out.println("fallback executed becoz service is down " + ex.getMessage());
        log.info("fallback executed becoz service is down " + ex.getMessage());
        User user = User.builder().name("dummy").email("dummy@gmail.com").userId(8).build();
        return new ResponseEntity<>(user, HttpStatus.BAD_REQUEST);
    }
}
