package com.example.edutopia_res.Iservices;

import com.example.edutopia_res.entities.Report;
import com.example.edutopia_res.entities.User;

import java.util.List;

public interface IreportService {
    void reportDish(int dishId, User user, String title, String note);

    List<Report> getArchivedReports();
    void traiterReclamation(int reportId);

    List<Report> getAllReports();
}
