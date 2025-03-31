package com.gaurav.ProfileManagement.controller;

import com.gaurav.ProfileManagement.user.User;
import com.gaurav.ProfileManagement.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;
    private final UserDetailsService userDetailsService;

    public User getLoggedInUser(){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String username=authentication.getName();
        return repository.findByEmail(username).orElseThrow(()-> new UsernameNotFoundException("User Does not Exist"));
    }

    public User updateProfile(UserProfileUpdateRequest  request){
        User user=getLoggedInUser();
        user.setEmail(request.getEmail());
        user.setName(request.getName());
        User updatedUser=repository.save(user);
        UserDetails newUserDetails = userDetailsService.loadUserByUsername(updatedUser.getUsername());
        Authentication newAuth = new UsernamePasswordAuthenticationToken(newUserDetails, null, newUserDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(newAuth);
        return updatedUser;
    }
}
