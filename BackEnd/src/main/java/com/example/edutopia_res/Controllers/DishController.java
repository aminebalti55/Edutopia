package com.example.edutopia_res.Controllers;


import com.example.edutopia_res.Iservices.IDishService;
import com.example.edutopia_res.Iservices.IuserService;
import com.example.edutopia_res.Repository.UserRepository;
import com.example.edutopia_res.entities.Category;
import com.example.edutopia_res.entities.Dish;
import com.example.edutopia_res.entities.SpicinessLevel;
import com.example.edutopia_res.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = {"*"})

    @RequestMapping("/dishes")
    public class DishController {

    @Autowired
    IDishService dishService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    IuserService userservice;

    @PostMapping("/addDish")
    public Dish createDish(@RequestParam("name") String name,
                           @RequestParam("description") String description,
                           @RequestParam("price") float price,
                           @RequestParam(value = "image", required = false) MultipartFile image,
                           @RequestParam("category") Category category,
                           @RequestParam("spicinessLevel") SpicinessLevel spicinessLevel) throws IOException {
        return dishService.createDish(name, description, price, image, category, spicinessLevel);
    }




    @GetMapping("/Getalldishes")
    public List<Dish> getAllDishes() {
        List<Dish> dishes = dishService.getAllDishes();
        List<Dish> dishDtos = new ArrayList<>();

        for (Dish dish : dishes) {
            Dish dishDto = new Dish();
            dishDto.setDishId(dish.getDishId());
            dishDto.setName(dish.getName());
            dishDto.setDescription(dish.getDescription());
            dishDto.setPrice(dish.getPrice());
            dishDto.setCategory(dish.getCategory());
            dishDto.setSpicinessLevel(dish.getSpicinessLevel());

            if (dish.getDishimageData() != null) {
                String image = Base64.getEncoder().encodeToString(dish.getDishimageData());
                dishDto.setImage(image);
            }

            dishDtos.add(dishDto);
        }

        return dishDtos;
    }

    @GetMapping("/getdishbyid/{id}")
    public ResponseEntity<Dish> getDishById(@PathVariable int id) {
        Optional<Dish> dish = dishService.getDishById(id);
        if (dish.isPresent()) {
            return new ResponseEntity<>(dish.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/highest-rated")
    public ResponseEntity<Dish> getHighestRatedDish() {
        Dish dish = dishService.getHighestRatedDish();
        if (dish == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(dish);
        }
    }

    @GetMapping("/top3HighestRated")
    public List<Dish> getTop3HighestRatedDishes() throws Exception {
        return dishService.getTop3HighestRatedDishes();
    }


    @PutMapping("/Update/{id}")
    public ResponseEntity<Dish> updateDish(@PathVariable("id") int id,
                                           @RequestParam(value = "name", required = false) String name,
                                           @RequestParam(value = "description", required = false) String description,
                                           @RequestParam(value = "price", required = false) Float price,
                                           @RequestParam(value = "image", required = false) MultipartFile image,
                                           @RequestParam(value = "category", required = false) Category category,
                                           @RequestParam(value = "spicinessLevel", required = false) SpicinessLevel spicinessLevel) throws IOException {

        Dish dish = dishService.updateDish(id, name, description, price, image, category, spicinessLevel);
        return new ResponseEntity<>(dish, HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDish(@PathVariable int id) {
        dishService.deleteDish(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }








    @GetMapping("/generate")
    public String generateRssFeed() {
        Dish bestRatedDish = dishService.getHighestRatedDish();
        return dishService.generateRssFeed(bestRatedDish);
    }

    @PostMapping("/send")
    public ResponseEntity<String> sendRssNewsletter() {
        List<User> subscribedUsers = userservice.getSubscribedUsers();

        // Check if there are subscribed users
        if (subscribedUsers.isEmpty()) {
            return ResponseEntity.badRequest().body("No subscribed users found.");
        }
       else  dishService.sendRssNewsletter();
        return ResponseEntity.ok("RSS newsletter sent successfully!");
    }


    @GetMapping("/{username}")
    public ResponseEntity<List<Dish>> getRecommendedDishes(@PathVariable String username) throws Exception {
        List<Dish> recommendedDishes = dishService.getRecommendedDishes(username);
        return ResponseEntity.ok(recommendedDishes);
    }

}

