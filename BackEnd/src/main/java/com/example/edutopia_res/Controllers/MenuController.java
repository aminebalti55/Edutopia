package com.example.edutopia_res.Controllers;

import com.example.edutopia_res.Iservices.IDishService;
import com.example.edutopia_res.Iservices.ImenuService;
import com.example.edutopia_res.Repository.DishRepository;
import com.example.edutopia_res.Repository.MenuRepository;
import com.example.edutopia_res.entities.Dish;
import com.example.edutopia_res.entities.ImageUtils;
import com.example.edutopia_res.entities.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@RestController
@RequestMapping("/menus")
@CrossOrigin(origins = {"*"})

public class MenuController {
    @Autowired
    ImenuService MenuService;
    @Autowired
    DishRepository dishRepository;
    @Autowired
    MenuRepository menuRepository;

    @PostMapping("/update")
    public ResponseEntity<Map<String, String>> updateMenu() {
        try {
            MenuService.updateMenu();
            Map<String, String> response = new HashMap<>();
            response.put("message", "Menu updated successfully.");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "Failed to update menu: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }


    @GetMapping("/dishes/{id}")
    public ResponseEntity<Menu> getMenuWithDishes(@PathVariable int id) throws Exception {
        Optional<Menu> menu = menuRepository.findById(id);
        if (menu.isPresent()) {
            Menu fetchedMenu = menu.get();
            List<Dish> dishes = dishRepository.findByMenu_MenuId(fetchedMenu.getMenuId());

            for (Dish dish : dishes) {
                String imagePath = dish.getImage();
                String base64Image = ImageUtils.encodeImageToBase64(imagePath);
                dish.setImage(base64Image);
            }

            fetchedMenu.setDishes(dishes);
            return ResponseEntity.ok(fetchedMenu);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/getallmenus")
    public ResponseEntity<List<Menu>> getAllMenus() throws Exception {
        List<Menu> menus = menuRepository.findAll();
        if (menus.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            for (Menu menu : menus) {
                for (Dish dish : menu.getDishes()) {
                    String imagePath = dish.getImage();
                    String base64Image = ImageUtils.encodeImageToBase64(imagePath);
                    dish.setImage(base64Image);
                }
            }
            return ResponseEntity.ok(menus);
        }
    }
}
