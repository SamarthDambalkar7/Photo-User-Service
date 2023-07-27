package com.userservice.userservice.controller;

import com.userservice.userservice.model.User;
import com.userservice.userservice.service.UserServiceImpl;
import com.userservice.userservice.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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


    @GetMapping("/")
    public ResponseEntity<?> getUserByUserId(@RequestParam String userId, @RequestParam Boolean isCurrentUser) {

        if (isCurrentUser) {
            return userServiceImpl.getUserByUserId(userId, isCurrentUser);
        } else {
            isCurrentUser = false;
            return userServiceImpl.getUserByUserId(userId, isCurrentUser);
        }
    }

    //photo property logic
    @PutMapping("/photocount")
    public ResponseEntity<?> incrementPhotocount(@RequestParam String userId) {
        return userServiceImpl.incrementPhotoCounter(userId);
    }

    //followers / following users logic
    @PutMapping("/follower")
    public ResponseEntity<?> addFollower(@RequestParam String followerId, @RequestParam String followingId) {
        return userServiceImpl.addFollower(followerId, followingId);
    }

    @PutMapping("/photo/save")
    public HttpStatus savePhoto(@RequestParam String userId, @RequestParam String photoUrl) {
        return userServiceImpl.savePhoto(userId, photoUrl);
    }

    @PutMapping("/photo/unsave")
    public HttpStatus unSavePhoto(@RequestParam String userId, @RequestParam String photoUrl) {
        return userServiceImpl.unSavePhoto(userId, photoUrl);
    }

}
