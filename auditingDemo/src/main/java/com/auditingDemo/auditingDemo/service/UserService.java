package com.auditingDemo.auditingDemo.service;


import com.auditingDemo.auditingDemo.entity.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    User updateUser(User user, int id);
}
