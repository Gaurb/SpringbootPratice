package com.example.CRUD.controller;

import com.example.CRUD.model.User;
import com.example.CRUD.service.UserService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;



    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable ObjectId id) {
        Optional<User> user = userService.getUserById(id);
        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }
    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        String username=SecurityContextHolder.getContext().getAuthentication().getName();
        User existingUser = userService.findByUsername(username);
        if (existingUser!=null) {
            existingUser.setPassword(user.getPassword());
            existingUser.setUsername(user.getUsername());
            userService.createUser(existingUser);
            return ResponseEntity.ok(existingUser);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }

    @DeleteMapping
    public ResponseEntity<?>deleteUserById(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userService.deleteUserByUserName(authentication.getName());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @GetMapping
    public ResponseEntity<?>greeting(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return new ResponseEntity<>("Hi"+ authentication.getName(),HttpStatus.OK);
    }
}
