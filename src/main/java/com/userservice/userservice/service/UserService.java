package com.userservice.userservice.service;

import com.userservice.userservice.dto.UserDTO;
import com.userservice.userservice.model.User;
import org.springframework.http.ResponseEntity;

public interface UserService {

    ResponseEntity<?> createNewUser(User user);

    ResponseEntity<?> loginUser(User user);

    UserDTO getUserByUserId(String userId);

}
