package com.example.retosflags.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.retosflags.model.User;
import com.example.retosflags.service.UserService;


@Controller
public class LoginController {

    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @PostMapping("/login")
    public String logUser(@RequestParam String username, @RequestParam String password,Model model, HttpSession session) {
        User user=new User(username,password);
        User loggedUser=userService.logUser(user);
        if(loggedUser==null){
            model.addAttribute("error", "Usuario o contraseña incorrectos");
            return "error";
        }
        session.setAttribute("user", loggedUser);
        session.setAttribute("username", loggedUser.getUsername());
        return "redirect:/home";
    }
    
    @PostMapping("/register")
    public String registerUser(@RequestParam String username, @RequestParam String password,Model model, HttpSession session) {
        User user=new User(username,passwordEncoder.encode(password));
        userService.addUser(user);
        session.setAttribute("user", user);
        session.setAttribute("username", user.getUsername());
        return "redirect:/home";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        return "redirect:/";
    }
}
