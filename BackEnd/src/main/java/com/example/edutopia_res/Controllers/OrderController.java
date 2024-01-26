package com.example.edutopia_res.Controllers;

import com.example.edutopia_res.Iservices.IorderService;
import com.example.edutopia_res.Iservices.IuserService;
import com.example.edutopia_res.Iservices.iLOYALTYservice;
import com.example.edutopia_res.Repository.OrderRepository;
import com.example.edutopia_res.Repository.UserRepository;
import com.example.edutopia_res.entities.Order;
import com.example.edutopia_res.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    IuserService userService;
    @Autowired
    iLOYALTYservice loyaltyProgramService;



    @PostMapping("/awardPoints")
    public ResponseEntity<?> awardLoyaltyPoints(@RequestParam int userId) {
        loyaltyProgramService.Orderadish(userId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/rewards")
    public ResponseEntity<List<String>> getRewards(@RequestParam int userId) {
        List<String> rewards = loyaltyProgramService.getRewards(userId);
        return ResponseEntity.ok(rewards);
    }

    @PostMapping("{userId}/badges")
    public ResponseEntity<Void> assignBadgeToUser(@PathVariable int userId) {
        loyaltyProgramService.assignBadgeToUser(userId);
        return ResponseEntity.ok().build();
    }
}
