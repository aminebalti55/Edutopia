package com.example.edutopia_res.Iservices;

import com.example.edutopia_res.entities.QueueStatus;
import com.example.edutopia_res.entities.User;


public interface IQueueManagementService {
    QueueStatus getQueueStatus();

    void addUserToQueue(User user);

    void removeUserFromQueue(User user);
}
