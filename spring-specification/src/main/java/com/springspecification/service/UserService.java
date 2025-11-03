package com.springspecification.service;

import com.springspecification.model.InternalUser;
import com.springspecification.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    
    public List<InternalUser> getAllUsers() {
        return userRepository.findAll();
    }
    
    public InternalUser getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }
    
    public InternalUser createUser(InternalUser internalUser) {
        return userRepository.save(internalUser);
    }
}