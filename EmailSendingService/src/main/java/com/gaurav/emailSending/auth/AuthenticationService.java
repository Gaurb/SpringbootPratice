package com.gaurav.emailSending.auth;

import com.gaurav.emailSending.config.JwtService;
import com.gaurav.emailSending.model.AuthenticationRequest;
import com.gaurav.emailSending.model.AuthenticationResponse;
import com.gaurav.emailSending.model.RegisterRequest;
import com.gaurav.emailSending.service.EmailService;
import com.gaurav.emailSending.user.Role;
import com.gaurav.emailSending.user.User;
import com.gaurav.emailSending.user.UserRepository;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user=userRepository.findByEmail(request.getEmail()).orElseThrow();
        var jwtToken=jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }

    public AuthenticationResponse register(RegisterRequest request) {
        var user=User.builder()
                .email(request.getEmail())
                .name(request.getName())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        userRepository.save(user);

        var jwtToken=jwtService.generateToken(user);

        try {
            emailService.sendWelcomeEmail(request.getEmail(), request.getName(), "https://github.com/gaurb");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        return AuthenticationResponse.builder().token(jwtToken).build();
    }
}
