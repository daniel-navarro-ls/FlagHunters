package com.example.retosflags.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.example.retosflags.model.User;
import com.example.retosflags.service.UserService;

@Controller
public class DataBaseUsage {

    @Autowired
    private UserService userService;

    void run(String... args) throws Exception {
        // Lógica para usar userService y manejar la base de datos
        User user;
        user = new User();
        user.setUsername("admin");
        user.setPassword("password");
        userService.addUser(user);
    }


}
