package com.example.edutopia_res.Controllers;

import com.example.edutopia_res.Iservices.IDishService;
import com.example.edutopia_res.Iservices.IratingService;
import com.example.edutopia_res.entities.Ratings;
import com.example.edutopia_res.entities.User;
import org.keycloak.KeycloakSecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/ratings")
@CrossOrigin(origins = {"*"})
public class RatingController {
    @Autowired
    IratingService ratingservice;


  /*  @PostMapping("/{userId}/{dishId}/{score}")
    public ResponseEntity<String> rateDish(@PathVariable int userId, @PathVariable int dishId, @PathVariable double score) {

        ratingservice.rateDish(userId, dishId, score);

        return ResponseEntity.ok("Rating saved successfully");
    }*/

    @PostMapping("/rate/{dishId}")
    public ResponseEntity<String> rateDish(@PathVariable int dishId, @RequestParam int userId, @RequestParam double score) {
        try {
            ratingservice.rateDish(userId, dishId, score);
            HttpHeaders headers = new HttpHeaders();
            headers.setCacheControl(CacheControl.noCache().getHeaderValue());
            return ResponseEntity.ok().headers(headers).body("Rating added successfully");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

}




