package com.userservice.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDTO {

    private String userName;

    private List<String> followers = new ArrayList<>();

    private List<String> following = new ArrayList<>();

    private long photosCount;

}
