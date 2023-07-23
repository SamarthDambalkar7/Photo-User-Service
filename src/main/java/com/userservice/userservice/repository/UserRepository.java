package com.userservice.userservice.repository;

import com.userservice.userservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {

    User findByUserId(String userId);

    Boolean existsByUserName(String userName);


}
