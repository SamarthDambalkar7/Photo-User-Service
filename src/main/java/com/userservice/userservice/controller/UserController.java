package com.userservice.userservice.controller;

import com.userservice.userservice.model.User;
import com.userservice.userservice.service.UserServiceImpl;
import com.userservice.userservice.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Autowired
    private JwtUtil jwtUtil;


    /* user crud operations */

    @PostMapping("/addnewuser")
    public ResponseEntity<?> addNewUser(@RequestBody User user) {
        return userServiceImpl.createNewUser(user);
    }

    @PostMapping("/test")
    public String getTest(@RequestBody User user) {

        return jwtUtil.generateToken(user);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User user) {

        
        return userServiceImpl.loginUser(user);
    }

}
