package com.userservice.userservice.service;

import com.userservice.userservice.dto.UserDTO;
import com.userservice.userservice.model.User;
import com.userservice.userservice.repository.UserRepository;
import com.userservice.userservice.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
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

        if (!bCryptPasswordEncoder.matches(encodedUserPassword, userRepository.findByUserId(user.getUserId()).getPassword())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        String jwtToken = jwtUtil.generateToken(user);

        return ResponseEntity.status(HttpStatus.OK).body(jwtToken);
    }

    @Override
    public ResponseEntity<?> getUserByUserId(String userId, Boolean isCurrentUser) {
        UserDTO userDTO = new UserDTO();

        User fetchedUser = userRepository.findByUserId(userId);

        if (isCurrentUser) {
            userDTO.setUserId(fetchedUser.getUserId());
            userDTO.setUserName(fetchedUser.getUserName());
            userDTO.setFollowers(fetchedUser.getFollowers());
            userDTO.setFollowing(fetchedUser.getFollowing());
            userDTO.setPhotosCount(fetchedUser.getPhotosCount());
            userDTO.setSavedPhotos(fetchedUser.getSavedPhotos());
        } else {
            userDTO.setUserId(fetchedUser.getUserId());
            userDTO.setUserName(fetchedUser.getUserName());
            userDTO.setFollowers(fetchedUser.getFollowers());
            userDTO.setFollowing(fetchedUser.getFollowing());
            userDTO.setPhotosCount(fetchedUser.getPhotosCount());
        }

        return ResponseEntity.ok(userDTO);
    }

    @Override
    public ResponseEntity<?> incrementPhotoCounter(String userId) {

        //fetch user from database
        User fetchedUser = userRepository.findByUserId(userId);

        //set new value / increment
        fetchedUser.setPhotosCount(fetchedUser.getPhotosCount() + 1);

        //save to db
        userRepository.save(fetchedUser);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<?> addFollower(String followerId, String followingId) {

        //fetch users from db
        if (userRepository.existsById(followerId) && userRepository.existsById(followingId)) {

            User follower = userRepository.findByUserId(followerId);
            User following = userRepository.findByUserId(followingId);

            // update values
            follower.getFollowing().add(followingId);
            following.getFollowers().add(followerId);

            //store update values to database
            userRepository.save(follower);
            userRepository.save(following);
            return ResponseEntity.ok().build();

        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @Override
    public HttpStatus savePhoto(String userId, String photoUrl) {
        User fetchedUser = userRepository.findByUserId(userId);
        fetchedUser.getSavedPhotos().add(photoUrl);
        return HttpStatus.OK;
    }

    @Override
    public HttpStatus unSavePhoto(String userId, String photoUrl) {

        User fetchedUser = userRepository.findByUserId(userId);
        fetchedUser.getSavedPhotos().remove(photoUrl);

        return HttpStatus.OK;
    }


}
