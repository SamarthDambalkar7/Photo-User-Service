package com.userservice.userservice.service;

import com.userservice.userservice.dto.UserDTO;
import com.userservice.userservice.model.User;
import com.userservice.userservice.repository.UserRepository;
import com.userservice.userservice.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public ResponseEntity<?> createNewUser(User user) {

        if (userRepository.existsById(user.getUserId().toLowerCase())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("userid already exists");
        }
        if (userRepository.existsByUserName(user.getUserName().toLowerCase())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("userName already exists");
        }
        User savedUser = new User();
        savedUser.setUserName(user.getUserName());
        savedUser.setUserId(user.getUserId());
        savedUser.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        savedUser.setPhotosCount(0);
        userRepository.save(savedUser);
        return ResponseEntity.status(HttpStatus.OK).body(savedUser);


    }

    @Override
    public ResponseEntity<?> loginUser(User user) {
        String encodedUserPassword = user.getPassword();

        if (!userRepository.existsById(user.getUserId())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
//        if (!userRepository.findByUserId(user.getUserId()).getPassword().equals(bCryptPasswordEncoder.encode(encodedUserPassword))) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
//        }
        if (!bCryptPasswordEncoder.matches(encodedUserPassword, userRepository.findByUserId(user.getUserId()).getPassword())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        String jwtToken = jwtUtil.generateToken(user);

        return ResponseEntity.status(HttpStatus.OK).body(jwtToken);
    }

    @Override
    public UserDTO getUserByUserId(String userId) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUserName(userRepository.findByUserId(userId).getUserName());
        userDTO.setFollowers(userRepository.findByUserId(userId).getFollowers());
        userDTO.setFollowing(userRepository.findByUserId(userId).getFollowing());
        userDTO.setPhotosCount(userRepository.findByUserId(userId).getPhotosCount());
        return userDTO;
    }

    @Override
    public ResponseEntity<?> incrementPhotoCounter(String userId) {
        User fetchedUser = userRepository.findByUserId(userId);
        fetchedUser.setPhotosCount(fetchedUser.getPhotosCount() + 1);
        userRepository.save(fetchedUser);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<?> addFollower(String followerId, String followingId) {
        //fetch users from db

        User follower = userRepository.findByUserId(followerId);
        User following = userRepository.findByUserId(followingId);

        // update values
        follower.getFollowing().add(followingId);
        following.getFollowers().add(followerId);

        //store update values to database
        userRepository.save(follower);
        userRepository.save(following);
        return ResponseEntity.ok().build();
    }
}
