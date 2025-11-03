package com.springspecification.controller;

import com.springspecification.dto.CreateUserDTO;
import com.springspecification.dto.UserSearchDTO;
import com.springspecification.model.InternalUser;
import com.springspecification.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    
    @Autowired
    private UserService userService;
    
    @GetMapping
    public List<InternalUser> getAllUsers() {
        return userService.getAllUsers();
    }
    
    @GetMapping("/{id}")
    public InternalUser getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }
    
    @GetMapping("/search")
    public List<InternalUser> searchUsers(@ModelAttribute UserSearchDTO searchDTO) {
        System.out.println(searchDTO);
        return null;
    }
    
    @PostMapping
    public InternalUser createUser(@RequestBody CreateUserDTO createUserDTO) {
        return userService.createUser(null);
    }
}
