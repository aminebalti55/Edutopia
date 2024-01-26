package com.example.edutopia_res.Iservices;

import com.example.edutopia_res.entities.Order;
import com.example.edutopia_res.entities.User;

import java.util.List;

public interface iLOYALTYservice {

    boolean isUserEligibleForRewards(User user);
    void Orderadish(int userId);
    List<String> getRewards(int userId);
    void assignBadgeToUser(int userId);
}
