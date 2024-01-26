package com.example.edutopia_res.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "User")

public class User implements UserDetails {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "user_id")
        private   int  userId;

        @Column(name = "id_card")
        private    int idCard;

    private   String firstname;

    private   String lastname;

    private   String email;

    private   String password;

    private String username;

    private int loyaltyPoints;

private String profile_pic;
    private byte[] profilePicData; // binary data for profile picture

    private int orderCount;

    @Enumerated(EnumType.STRING)
    private SpicinessLevel spicinessTolerance;

    private boolean isSubscribedToRss;

    private String claimedReward;
    private int pointsRequiredForReward;
    @JsonIgnore

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Badge> badges = new ArrayList<>();

@JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Order> orders = new ArrayList<>();


    @Column
    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String phoneNumber;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }



    public enum Gender {
        MALE, FEMALE
    }

    @Enumerated(EnumType.STRING)
        private  Role role;
    @JsonIgnore

    @OneToMany(cascade = CascadeType.ALL, mappedBy="user" ,orphanRemoval = true)
        private  Set<Ratings> ratings;
    @JsonIgnore

    @OneToMany(cascade = CascadeType.ALL, mappedBy="user" ,orphanRemoval = true)
    private Set<Report> reports;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Subscription subscription;
    @JsonIgnore
    @OneToOne(mappedBy = "user")
    private QueueEntry queueEntry;
    @Column(name = "position_in_queue")
    private Integer positionInQueue;

        }



