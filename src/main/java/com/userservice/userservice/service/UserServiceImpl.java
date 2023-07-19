package com.userservice.userservice.service;

import com.userservice.userservice.dto.UserDTO;
import com.userservice.userservice.model.User;
import com.userservice.userservice.repository.UserRepository;
import com.userservice.userservice.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public ResponseEntity<?> createNewUser(User user) {

        if (userRepository.existsById(user.getUserId().toLowerCase())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("userid already exists");
        }
        if (userRepository.existsByUserName(user.getUserName().toLowerCase())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("userName already exists");
        }
        User savedUser = userRepository.save(user);
        return ResponseEntity.status(HttpStatus.OK).body(savedUser);


    }

    @Override
    public ResponseEntity<?> loginUser(User user) {
        if (!userRepository.existsById(user.getUserId())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        if (!userRepository.findByUserId(user.getUserId()).getPassword().equals(user.getPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        String jwtToken = jwtUtil.generateToken(user);

        return ResponseEntity.status(HttpStatus.OK).body(jwtToken);
    }

    @Override
    public UserDTO getUserByUserId(String userId) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUserName(userRepository.findByUserId(userId).getUserName());
        return userDTO;

    }

}
