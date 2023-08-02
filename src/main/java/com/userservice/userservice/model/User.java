package com.userservice.userservice.model;

import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Users")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Transactional
public class User {

    @Id
    @Column(name = "userId")
    private String userId;

    @Column(name = "password")
    private String password;

    @Column(name = "userName")
    private String userName;

    @Column(name = "photosCount")
    private long photosCount;

    @ElementCollection
    @CollectionTable(name = "user_followers", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "followers")
    private Set<String> followers = new HashSet<>();

    @ElementCollection
    @CollectionTable(name = "user_following", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "following")
    private Set<String> following = new HashSet<>();

    @ElementCollection
    @CollectionTable(name = "user_saved_photos", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "saved_photos")
    private Set<String> savedPhotos = new HashSet<>();
}
