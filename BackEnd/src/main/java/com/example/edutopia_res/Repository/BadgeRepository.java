package com.example.edutopia_res.Repository;

import com.example.edutopia_res.entities.Badge;
import com.example.edutopia_res.entities.BadgeType;
import com.example.edutopia_res.entities.Dish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BadgeRepository extends JpaRepository<Badge, Integer> {



}

