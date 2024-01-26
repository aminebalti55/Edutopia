package com.example.edutopia_res.Repository;

import com.example.edutopia_res.entities.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.OptionalDouble;

@Repository
public interface DishRepository extends JpaRepository<Dish, Integer> {
    Dish findTopByOrderByScoreDesc();
    List<Dish> findBySpicinessLevelLessThanEqual(SpicinessLevel spicinessLevel);
    List<Dish> findByCategory(Category category);

    List<Dish> findByMenu_MenuId(int menuId);

    List<Dish> findTop3ByOrderByScoreDesc();

}