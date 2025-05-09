package com.example.CRUD.controller;

import com.example.CRUD.model.User;
import com.example.CRUD.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UserService userService;

    @GetMapping("/all-users")
    public ResponseEntity<?> getALlUsers() {
        List<User> all=userService.getAllUsers();
        if(all!=null && !all.isEmpty()){
            return ResponseEntity.ok(all);
        }
        else{
            return ResponseEntity.status(404).body("No users found");
        }
    }

    @PostMapping("/add-admin")
    public ResponseEntity<?> addAdmin(User user) {
        User newUser = userService.addAdmin(user);
        if (newUser != null) {
            return ResponseEntity.ok(newUser);
        } else {
            return ResponseEntity.status(400).body("Failed to add admin");
        }
    }

}
