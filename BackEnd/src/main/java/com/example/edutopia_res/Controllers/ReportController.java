package com.example.edutopia_res.Controllers;

import com.example.edutopia_res.Iservices.IDishService;
import com.example.edutopia_res.Iservices.IratingService;
import com.example.edutopia_res.Iservices.IreportService;
import com.example.edutopia_res.Repository.ReportRepository;
import com.example.edutopia_res.Repository.UserRepository;
import com.example.edutopia_res.entities.Report;
import com.example.edutopia_res.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/Reports")
public class ReportController {
    @Autowired
    IreportService Reportservice;
    @Autowired
    UserRepository userRepository;


    @Autowired
    ReportRepository reportRepository;

    @PostMapping("/create")
    public ResponseEntity<?> reportDish(@PathVariable("dishId") int dishId,
                                        @RequestParam("userId") int userId,
                                        @RequestParam("title") String title,
                                        @RequestParam("note") String note) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));


        Reportservice.reportDish(dishId, user, title, note);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/reports/statistics")
    public ResponseEntity<Map<String, Long>> displayStatistics() {
        long treatedReportsCount = reportRepository.countTreatedReports();
        long untreatedReportsCount = reportRepository.countUntreatedReports();

        Map<String, Long> statistics = new HashMap<>();
        statistics.put("treatedReportsCount", treatedReportsCount);
        statistics.put("untreatedReportsCount", untreatedReportsCount);

        return ResponseEntity.ok(statistics);
    }

    @GetMapping("/archived")
    public List<Report> getArchivedReports() {
        return Reportservice.getArchivedReports();
    }

    @PutMapping("/report/{reportId}/traiter")
    public void traiterReclamation(@PathVariable("reportId") int reportId) {
        Reportservice.traiterReclamation(reportId);

    }
    @GetMapping("/reports")
    public List<Report> getAllReports() {
        return Reportservice.getAllReports();
    }
}
