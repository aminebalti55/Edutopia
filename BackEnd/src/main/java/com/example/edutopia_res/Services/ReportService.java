package com.example.edutopia_res.Services;

import com.example.edutopia_res.Iservices.IreportService;
import com.example.edutopia_res.Repository.DishRepository;
import com.example.edutopia_res.Repository.ReportRepository;
import com.example.edutopia_res.entities.Dish;
import com.example.edutopia_res.entities.Report;
import com.example.edutopia_res.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service

public class ReportService implements IreportService {
@Autowired
    DishRepository dishRepository;

    @Autowired
    ReportRepository reportRepository;


    public void reportDish(int dishId, User user, String title, String note) {
        Optional<Dish> optionalDish = dishRepository.findById(dishId);
        if (optionalDish.isPresent()) {
            Dish dish = optionalDish.get();

            if (dish.isArchived()) {
                return;
            }

            List<Report> reports = reportRepository.findByDishAndUser(dish, user);
            if (!reports.isEmpty()) {
                return;
            }

            Report report = new Report();
            report.setDish(dish);
            report.setUser(user);
            report.setTitle(title);
            report.setNote(note);
            report.setUrgent(false);
            report.setCreatedAt(new Date());
            report.setClosedAt(null);
            report.setTraitee(false);

            // Save the report
            reportRepository.save(report);

            if (reportRepository.countByDish(dish) >= 2) {
                // Archive the dish
                dish.setArchived(true);
                report.setUrgent(true);
                dishRepository.save(dish);
            }
        }



}


    public List<Report> getArchivedReports() {
        Date today = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);
        calendar.add(Calendar.DAY_OF_MONTH, -30); // soustrait 30 jours à la date d'aujourd'hui
        Date thresholdDate = calendar.getTime();

        return reportRepository.findByTraiteeTrueAndCreatedAtBefore(thresholdDate);
    }

    public List<Report> getAllReports() {
        return reportRepository.findByTraiteeFalse();
    }
    public void traiterReclamation(int reportId) {
        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> new EntityNotFoundException("Report not found with id " + reportId));

        report.setTraitee(true);

        reportRepository.save(report);
    }
}
