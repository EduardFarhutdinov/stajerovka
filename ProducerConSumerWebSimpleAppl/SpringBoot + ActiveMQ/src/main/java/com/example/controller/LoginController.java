package com.example.controller;

import com.example.Service.UserService;
import com.example.model.User;
import com.example.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

//@Controller
public class LoginController {
//    @Autowired
//    private UserRepo userRepo;
//    @Autowired
//    UserService userService;
//
//    @GetMapping("/login")
//    public String login() {
//        return "login";
//    }
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    @PostMapping("/login")
//    public String login(User user) {
//        User userFrombd = userRepo.findByUsername(user.getUsername());
//        if (userFrombd == null) {
//            throw new UsernameNotFoundException("User not found");
//        } else {
//            String authPass = user.getUsername().concat(user.getPassword());
//            System.out.println(user.getUsername() + " " +user.getPassword());
//            if (passwordEncoder.matches(authPass, userFrombd.getPassword())) ;
//            {
//                return "redirect:/main";
//            }
//
//        }
//    }
}
