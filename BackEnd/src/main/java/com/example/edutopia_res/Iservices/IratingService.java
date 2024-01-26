package com.example.edutopia_res.Iservices;

import com.example.edutopia_res.entities.Ratings;
import org.keycloak.KeycloakSecurityContext;

public interface IratingService {
//   Ratings addRatingForDish(int dishId, int score, KeycloakSecurityContext context);
   // float getAverageRatingForDish(int dishId);
   // void rateDish(int dishId, int score);
  //  int calculateNewScore(int currentScore, int numRatings, int newRating);

  //  void rateDish(int userId, int dishId, double score);
  void rateDish(int userId, int dishId, double score);
}
