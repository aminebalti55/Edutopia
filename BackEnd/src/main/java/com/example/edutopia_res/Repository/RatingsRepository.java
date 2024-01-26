package com.example.edutopia_res.Repository;

import com.example.edutopia_res.entities.Dish;
import com.example.edutopia_res.entities.Ratings;
import com.example.edutopia_res.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;

@Repository
public interface RatingsRepository extends JpaRepository<Ratings, Integer> {
    Optional<Ratings> findByUserAndDish(User user, Dish dish);


    List<Ratings> findByDishDishId(int dishId);


    //  List<Ratings> findByDishId(int dishId);
}