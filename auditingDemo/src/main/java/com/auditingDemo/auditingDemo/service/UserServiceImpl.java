package com.auditingDemo.auditingDemo.service;

import com.auditingDemo.auditingDemo.entity.User;
import com.auditingDemo.auditingDemo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service // Marks this class as a Spring service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User updateUser(User user, int id) {
        // Fetch the existing user by ID
        Optional<User> existingUserOpt = userRepository.findById(id);

        if (existingUserOpt.isPresent()) {
            User existingUser = existingUserOpt.get();

            // Update the necessary fields
            existingUser.setAddress(user.getAddress());
            existingUser.setRole(user.getRole());
            existingUser.setDescription(user.getDescription());
            existingUser.setTitle(user.getTitle());
            existingUser.setPhoneNumber(user.getPhoneNumber());

            // Save and return the updated user
            return userRepository.save(existingUser);
        } else {
            return null;
        }
    }
}
