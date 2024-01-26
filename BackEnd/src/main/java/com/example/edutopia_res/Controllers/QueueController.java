package com.example.edutopia_res.Controllers;

import com.example.edutopia_res.Iservices.IQueueManagementService;
import com.example.edutopia_res.entities.QueueStatus;
import com.example.edutopia_res.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/queue")
public class QueueController {

    @Autowired
    private IQueueManagementService queueManagementService;
/*
    @GetMapping("/status")
    public ResponseEntity<QueueStatus> getQueueStatus() {
        QueueStatus queueStatus = queueManagementService.getQueueStatus();
        return ResponseEntity.ok(queueStatus);
    } */

    @GetMapping("/statusTime")
    public int getQueueStatus() {
        QueueStatus queueStatus = queueManagementService.getQueueStatus();
        return queueStatus.getEstimatedWaitTimeInMinutes();
    }
    /* nb elev dans res*/
    @GetMapping("/statusCounte")
    public int getQueueStatusCount() {
        QueueStatus queueStatus = queueManagementService.getQueueStatus();
        int c = queueStatus.getUsersInQueue().size();
        return c ;
    }


    @PostMapping("/add-user")
    public ResponseEntity<Void> addUserToQueue(@RequestBody User user) {
        queueManagementService.addUserToQueue(user);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/remove-user")
    public ResponseEntity<Void> removeUserFromQueue(@RequestBody User user) {
        queueManagementService.removeUserFromQueue(user);
        return ResponseEntity.ok().build();
    }
}

