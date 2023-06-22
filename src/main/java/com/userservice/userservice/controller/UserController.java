package com.userservice.userservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.userservice.userservice.model.User;
import com.userservice.userservice.service.UserServiceImpl;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserServiceImpl userServiceImpl;

    /* user crud operations */

    @PostMapping("/addnewuser")
    public ResponseEntity<?> addNewUser(@RequestBody User user) {
        return userServiceImpl.createNewUser(user);
    }
}
