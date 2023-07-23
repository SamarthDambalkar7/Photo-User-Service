package com.userservice.userservice.controller;

import com.userservice.userservice.dto.UserDTO;
import com.userservice.userservice.model.User;
import com.userservice.userservice.service.UserServiceImpl;
import com.userservice.userservice.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@CrossOrigin("*")
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


    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User user) {

        return userServiceImpl.loginUser(user);
    }


    @GetMapping("/getuserbyuserid")
    public UserDTO getUserByUserId(@RequestParam String userId) {

        return userServiceImpl.getUserByUserId(userId);
    }

    //photo property logic
    @PutMapping("/incrementphotocount")
    public ResponseEntity<?> incrementPhotocount(@RequestParam String userId) {
        return userServiceImpl.incrementPhotoCounter(userId);
    }

    //followers / following users logic
    @PutMapping("/follower")
    public ResponseEntity<?> addFollower(@RequestParam String followerId, @RequestParam String followingId) {
        return userServiceImpl.addFollower(followerId, followingId);
    }

}
