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

    @PostMapping("/test")
    public String getTest(@RequestBody User user) {

        return jwtUtil.generateToken(user);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User user) {


        return userServiceImpl.loginUser(user);
    }

    @GetMapping("/gettest")
    public String testGateway(@RequestParam String msg) {

        return "hello gateway" + msg;
    }


    @GetMapping("/getuserbyuserid")
    public UserDTO getUserByUserId(@RequestParam String userId) {

        return userServiceImpl.getUserByUserId(userId);
    }

    @PutMapping("/incrementphotocount")
    public ResponseEntity<?> incrementPhotocount(@RequestParam String userId) {
        return userServiceImpl.incrementPhotoCounter(userId);
    }


}
