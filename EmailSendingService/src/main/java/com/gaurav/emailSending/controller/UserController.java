package com.gaurav.emailSending.controller;

import com.gaurav.emailSending.model.UserProfileUpdateRequest;
import com.gaurav.emailSending.service.UserService;
import com.gaurav.emailSending.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
   private final UserService userService;

    @GetMapping("/profile")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<User> getUserDetails(){
        return ResponseEntity.ok(userService.getLoggedInUser());
    }

    @PutMapping("/profile")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<User> updateUserDetails(@RequestBody UserProfileUpdateRequest request){
        return ResponseEntity.ok(userService.updateProfile(request));
    }

}
