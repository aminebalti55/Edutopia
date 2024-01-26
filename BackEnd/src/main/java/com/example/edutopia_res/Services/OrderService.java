package com.example.edutopia_res.Services;

import com.example.edutopia_res.Iservices.IorderService;
import com.example.edutopia_res.Repository.OrderRepository;
import com.example.edutopia_res.Repository.UserRepository;
import com.example.edutopia_res.entities.Order;
import com.example.edutopia_res.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class OrderService implements IorderService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
     LoyaltyService loyaltyService;

  /*  public Order createOrder(Order order, int userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());
        loyaltyService.awardPoints(user, order); // call awardPoints before saving the order
        Order savedOrder = orderRepository.save(order);
        return savedOrder;
    }




    public class UserNotFoundException extends RuntimeException {
        public UserNotFoundException(int userId) {
            super("Could not find user with ID: " + userId);
        }
    }*/






}
