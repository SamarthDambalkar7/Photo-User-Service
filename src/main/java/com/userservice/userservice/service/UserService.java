package com.userservice.userservice.service;

import com.userservice.userservice.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public interface UserService {

    ResponseEntity<?> createNewUser(User user);

    ResponseEntity<?> loginUser(User user);

    ResponseEntity<?> getUserByUserId(String userId, Boolean isCurrentUser);

    ResponseEntity<?> incrementPhotoCounter(String userId);

    ResponseEntity<?> addFollower(String followerId, String followingId);

    HttpStatus savePhoto(String userId, String photoUrl);

    HttpStatus unSavePhoto(String userId, String photoUrl);


}
