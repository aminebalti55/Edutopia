package com.example.edutopia_res.Services;

import com.example.edutopia_res.Iservices.IuserService;
import com.example.edutopia_res.Iservices.iLOYALTYservice;
import com.example.edutopia_res.Repository.*;
import com.example.edutopia_res.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service

public class LoyaltyService implements iLOYALTYservice {



    @Autowired
    UserRepository userRepository;

    @Autowired
    BadgeRepository badgeRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    DishRepository dishRepository;



    public void Orderadish(int userId) {

        User user = userRepository.findById(userId).orElse(null);

        user.setLoyaltyPoints(user.getLoyaltyPoints() + 5);
        user.setOrderCount(user.getOrderCount() + 1);

        userRepository.save(user);
    }

    public boolean isUserEligibleForRewards(User user) {

        return user.getLoyaltyPoints() >= 5;    }

    public List<String> getRewards(int userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null || !isUserEligibleForRewards(user)) {
            return Collections.emptyList();
        }

        List<String> rewards = new ArrayList<>();
         user = userRepository.findById(userId).orElse(null);
        if (user.getLoyaltyPoints() >= 5  && user.getLoyaltyPoints() < 10 ) {
            rewards.add("10% off your next order");
            user.setPointsRequiredForReward(user.getLoyaltyPoints() + 2);
            user.setClaimedReward("10% off");
             user.setLoyaltyPoints(0);



        }

        else if (user.getLoyaltyPoints() >= 10 && user.getLoyaltyPoints() > 20) {
            rewards.add("20% off your next order");
            rewards.add("Free soda with your next order");
            user.setPointsRequiredForReward(user.getLoyaltyPoints() + 2);
            user.setClaimedReward("20% off and free soda");
            user.setLoyaltyPoints(0);

        } else if (user.getLoyaltyPoints() >= 22  && user.getLoyaltyPoints() > 30) {
            rewards.add("50% off your next order");
            rewards.add("Free entree with your next order");
            user.setPointsRequiredForReward(user.getLoyaltyPoints() + 2);
            user.setClaimedReward("50% off and free entree");
            user.setLoyaltyPoints(0);

        }


        userRepository.save(user);

        return rewards;
    }

    public void assignBadgeToUser(int userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getLoyaltyPoints() >= 100) {
            Badge badge = new Badge();
            badge.setBadgeType(BadgeType.GOLD);
            badge.setUser(user);
            badgeRepository.save(badge);
        } else if (user.getLoyaltyPoints() >= 50) {
            Badge badge = new Badge();
            badge.setBadgeType(BadgeType.SILVER);
            badge.setUser(user);
            badgeRepository.save(badge);
        } else if (user.getLoyaltyPoints() >= 25) {
            Badge badge = new Badge();
            badge.setBadgeType(BadgeType.BRONZE);
            badge.setUser(user);
            badgeRepository.save(badge);
        }


    }}
