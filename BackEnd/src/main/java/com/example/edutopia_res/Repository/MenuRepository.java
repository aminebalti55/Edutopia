package com.example.edutopia_res.Repository;

import com.example.edutopia_res.entities.Category;
import com.example.edutopia_res.entities.Dish;
import com.example.edutopia_res.entities.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Integer> {


}