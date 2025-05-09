package com.example.CRUD.service;

import com.example.CRUD.model.JournalEntry;
import com.example.CRUD.model.User;
import com.example.CRUD.repository.JournalRepository;
import com.example.CRUD.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder= new BCryptPasswordEncoder();


    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(List.of("USER"));
        return userRepository.save(user);
    }
    public User saveUser(User user) {
        return userRepository.save(user);
    }
    public Optional<User> getUserById(ObjectId id) {
        return userRepository.findById(id);
    }
    public void deleteUserById(ObjectId id) {
        userRepository.deleteById(id);
    }

    public void deleteUserByUserName(String username) {
         userRepository.deleteByUsername(username);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User addAdmin(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(List.of("USER","ADMIN"));
        return userRepository.save(user);
    }
}
