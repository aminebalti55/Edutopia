package com.example.edutopia_res.Repository;

import com.example.edutopia_res.entities.Dish;
import com.example.edutopia_res.entities.Report;
import com.example.edutopia_res.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report, Integer> {
    List<Report> findByDishAndUser(Dish dish, User user);
    long countByDish(Dish dish);


    List<Report> findByTraiteeTrueAndCreatedAtBefore(Date thresholdDate);
    @Query("SELECT r FROM Report r WHERE r.traitee = true AND r.createdAt < :thresholdDate")

    List<Report> findByTraiteeFalse();
    @Query("SELECT COUNT(r) FROM Report r WHERE r.traitee = true")
    long countTreatedReports();

    @Query("SELECT COUNT(r) FROM Report r WHERE r.traitee = false")
    long countUntreatedReports();

}