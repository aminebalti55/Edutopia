package com.example.edutopia_res.Services;

import com.example.edutopia_res.Iservices.ImenuService;
import com.example.edutopia_res.Repository.DishRepository;
import com.example.edutopia_res.Repository.MenuRepository;
import com.example.edutopia_res.Repository.ReportRepository;
import com.example.edutopia_res.entities.Category;
import com.example.edutopia_res.entities.Dish;
import com.example.edutopia_res.entities.ImageUtils;
import com.example.edutopia_res.entities.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.*;

@Service

public class MenuService implements ImenuService {
    @Autowired
    DishRepository dishRepository;
    @Autowired
    MenuRepository menuRepository;

    private static final Logger log = LoggerFactory.getLogger(MenuService.class);

    @Scheduled(cron = "0 0 7 ? * MON-FRI")

    public void updateMenu() throws Exception {

        List<Dish> breakfastDishes = dishRepository.findByCategory(Category.BREAKFAST);
       List<Dish> lunchDishes = dishRepository.findByCategory(Category.LUNCH);
       List<Dish> dinnerDishes = dishRepository.findByCategory(Category.DINNER);
       log.info("Retrieved dishes from database - Breakfast: {}, Lunch: {}, Dinner: {}", breakfastDishes.size(), lunchDishes.size(), dinnerDishes.size());

        Collections.shuffle(breakfastDishes);
        Collections.shuffle(lunchDishes);
        Collections.shuffle(dinnerDishes);

        log.info("Randomized breakfast dishes: {}", breakfastDishes);
        log.info("Randomized lunch dishes: {}", lunchDishes);
        log.info("Randomized dinner dishes: {}", dinnerDishes);

        Menu menu = new Menu();
        menu.setMenuName("Menu for " + LocalDate.now().toString());
        menu.setMenuDate(LocalDate.now());
        log.info("Creating new menu with name {} and date {}", menu.getMenuName(), menu.getMenuDate());

        if (!breakfastDishes.isEmpty()) {
            menu.getDishes().add(breakfastDishes.get(0));
        }
        if (!lunchDishes.isEmpty()) {
            menu.getDishes().add(lunchDishes.get(0));

        }
        if (!dinnerDishes.isEmpty()) {
            menu.getDishes().add(dinnerDishes.get(0));


        }

        menuRepository.save(menu);

        for (Dish dish : menu.getDishes()) {
            String imagePath = dish.getImage();
            String base64Image = ImageUtils.encodeImageToBase64(imagePath);
            dish.setImage(base64Image);
            dish.setMenu(menu);
            dishRepository.save(dish);
            log.info("Assigned menu ID to dish - Dish ID: {}, Menu ID: {}", dish.getDishId(), menu.getMenuId());

        }
    }
}
