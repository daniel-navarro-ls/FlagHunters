package com.example.retosflags.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.servlet.http.HttpSession;

import com.example.retosflags.model.User;
import com.example.retosflags.service.UserService;


@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public String logUser(@RequestParam String username, @RequestParam String password,Model model, HttpSession session) {
        User user=new User(username,password);
        User loggedUser=userService.logUser(user);
        if(loggedUser==null){
            model.addAttribute("error", "Usuario o contraseña incorrectos");
            return "error";
        }
        session.setAttribute("user", loggedUser);
        return "redirect:/home";
    }
    
    @PostMapping("/register")
    public String registerUser(@RequestParam String username, @RequestParam String password,Model model, HttpSession session) {
        User user=new User(username,password);
        userService.addUser(user);
        session.setAttribute("user", user);
        return "redirect:/home";
    }
}
