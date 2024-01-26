package com.example.edutopia_res.Repository;

import com.example.edutopia_res.entities.Order;
import com.example.edutopia_res.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findByUser(User user);

}