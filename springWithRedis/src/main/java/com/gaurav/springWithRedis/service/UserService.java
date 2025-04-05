package com.gaurav.springWithRedis.service;

import com.gaurav.springWithRedis.model.User;
import com.gaurav.springWithRedis.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.nio.file.attribute.UserPrincipalNotFoundException;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;


    public User getUser(int id){
        return repository.findById(id).orElseThrow();
    }


}
