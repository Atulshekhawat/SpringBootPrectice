package com.auditingDemo.auditingDemo.controller;


import com.auditingDemo.auditingDemo.entity.User;
import com.auditingDemo.auditingDemo.repository.UserRepository;
import com.auditingDemo.auditingDemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/User")
public class UserController {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @GetMapping("/getAllUser")
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @PutMapping("/update/{id}")
    public User updateUser(@RequestBody User user, @PathVariable int id) {
        return userService.updateUser(user,id);
    }

    @PostMapping("/create")
    User createUser(@RequestBody User user) {
        return userRepository.save(user);
    }


}
