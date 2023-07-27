package com.userservice.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDTO {

    private String userId;

    private String userName;

    private Set<String> followers = new HashSet<>();

    private Set<String> following = new HashSet<>();

    private Set<String> savedPhotos = new HashSet<>();

    private long photosCount;

}
