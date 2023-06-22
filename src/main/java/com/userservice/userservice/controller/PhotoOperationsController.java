package com.userservice.userservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.userservice.userservice.dto.UserDTO;
import com.userservice.userservice.service.UserServiceImpl;

@RestController
@RequestMapping("/userphoto")
public class PhotoOperationsController {

    // @Autowired
    // private WebClient.Builder webClientBuilder;

    @Autowired
    UserServiceImpl userServiceImpl;

    @GetMapping("/getallphotos")
    public void getAllPhotos(@RequestParam String userId) {

        // LinkedList<Photo> allPhotos = webClientBuilder.build().get()
        // .uri("http://localhost:8081/photo/getphotosbyuserid?userId=" +
        // userId).retrieve()
        // .bodyToMono(LinkedList.class).block();

    }

    @GetMapping("/getuserbyuserid")
    public UserDTO getUserByUserId(@RequestParam String userId) {

        return userServiceImpl.getUserByUserId(userId);
    }

}
