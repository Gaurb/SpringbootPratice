package com.gaurav.emailSending.service;

import com.gaurav.emailSending.model.UserProfileUpdateRequest;
import com.gaurav.emailSending.user.User;
import com.gaurav.emailSending.user.UserRepository;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;
    private final UserDetailsService userDetailsService;
    private final EmailService emailService;

    public User getLoggedInUser(){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String username=authentication.getName();
        return repository.findByEmail(username).orElseThrow(()-> new UsernameNotFoundException("User Does not Exist"));
    }

    public User updateProfile(UserProfileUpdateRequest request){
        User user=getLoggedInUser();
        user.setName(request.getName());
        User updatedUser=repository.save(user);
        UserDetails newUserDetails = userDetailsService.loadUserByUsername(updatedUser.getUsername());
        try {
            emailService.sendUpdateEmail(user.getEmail(), user.getName(), request.getName());
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        return updatedUser;
    }
}
