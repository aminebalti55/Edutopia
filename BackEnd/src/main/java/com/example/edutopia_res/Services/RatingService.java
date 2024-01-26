package com.example.edutopia_res.Services;

import com.example.edutopia_res.Iservices.IDishService;
import com.example.edutopia_res.Iservices.IratingService;
import com.example.edutopia_res.Repository.DishRepository;
import com.example.edutopia_res.Repository.RatingsRepository;
import com.example.edutopia_res.Repository.UserRepository;
import com.example.edutopia_res.entities.Dish;
import com.example.edutopia_res.entities.Ratings;
import com.example.edutopia_res.entities.User;
import org.keycloak.KeycloakSecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.*;

@Service

public class RatingService implements IratingService {
    @Autowired
    DishRepository dishRepository;
    @Autowired
    RatingsRepository ratingsRepository;
    @Autowired
    UserRepository userRepository;



    /*   public Ratings addRatingForDish(int dishId, int score, KeycloakSecurityContext context) {
            Optional<Dish> optionalDish = dishRepository.findById(dishId);
            if (!optionalDish.isPresent()) {
                return null;
            }

            if (score < 0 || score > 5) {
                throw new IllegalArgumentException("Score must be between 0 and 5");
            }

            String userId = context.getToken().getSubject();
            User user = new User();
            user.setUserId(userId);

            Dish dish = optionalDish.get();
            Ratings rating = new Ratings();
            rating.setScore(score);
            rating.setUser(user);
            rating.setCreatedAt(new Date());
            rating.setDishes(Collections.singletonList(dish));

            return ratingsRepository.save(rating);
        }*/




    public void rateDish(int userId, int dishId, double score) {
        if (score < 0 || score > 5) {
            throw new IllegalArgumentException("Invalid score value. Score must be between 0 and 5.");
        }

        // Find the user and dish entities
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        Dish dish = dishRepository.findById(dishId)
                .orElseThrow(() -> new EntityNotFoundException("Dish not found"));

        // Check if the user has already rated this dish
        Optional<Ratings> existingRating = ratingsRepository.findByUserAndDish(user, dish);

        if (existingRating.isPresent()) {
            throw new RuntimeException("User has already rated this dish.");

        } else {

            Ratings rating = new Ratings();
            rating.setScore(score);
            rating.setCreatedAt(new Date());
            rating.setUser(user);
            rating.setDish(dish);
            ratingsRepository.save(rating);
        }

         dish = dishRepository.findById(dishId).orElseThrow(() -> new RuntimeException("Dish not found"));
        List<Ratings> ratings = ratingsRepository.findByDishDishId(dishId);
        double totalScore = 0;
        int numRatings = 0;

        for (Ratings rating : ratings) {
            totalScore += rating.getScore();
            numRatings++;
        }

        double averageScore = numRatings > 0 ? totalScore / numRatings : 0;
        dish.setScore(averageScore);
        dish.setRatings(ratings);
        dishRepository.save(dish);
    }
}


