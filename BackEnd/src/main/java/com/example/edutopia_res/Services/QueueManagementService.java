package com.example.edutopia_res.Services;

import com.example.edutopia_res.Iservices.IQueueManagementService;
import com.example.edutopia_res.Repository.MenuRepository;
import com.example.edutopia_res.Repository.QueueRepository;
import com.example.edutopia_res.Repository.UserRepository;
import com.example.edutopia_res.entities.QueueEntry;
import com.example.edutopia_res.entities.QueueStatus;
import com.example.edutopia_res.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.List;

@Service
public class QueueManagementService implements IQueueManagementService {

    private static final int AVERAGE_SERVING_TIME_IN_MINUTES = 10;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private QueueRepository queueRepository;

    @Override
    public QueueStatus getQueueStatus() {
        QueueStatus queueStatus = new QueueStatus();

        // Get the current queue size
        int queueSize = queueRepository.getQueueSize();
        queueStatus.setCurrentQueueSize(queueSize);

        // Calculate the estimated wait time based on the average serving time and the current queue size
        int estimatedWaitTime = queueSize * AVERAGE_SERVING_TIME_IN_MINUTES;
        queueStatus.setEstimatedWaitTimeInMinutes(estimatedWaitTime);

        // Get the list of users in the queue
        List<User> usersInQueue = userRepository.getUsersInQueue();
        queueStatus.setUsersInQueue(usersInQueue);

        return queueStatus;
    }
    @Override
    public void addUserToQueue(User user) {

        // Create a new QueueEntry for the user
        QueueEntry queueEntry = new QueueEntry();
        queueEntry.setUser(user);
        queueEntry.setMenu(null);
        queueEntry.setTimestamp(LocalDateTime.now());

        // Add the QueueEntry to the queue
        queueRepository.save(queueEntry);

        // Update the user's position in the queue
        int positionInQueue = queueRepository.getQueueSize();
        user.setPositionInQueue(positionInQueue);
        userRepository.save(user);
    }
    @Override
    public void removeUserFromQueue(User user) {
        // Remove the user's QueueEntry from the queue
        QueueEntry queueEntry = queueRepository.getQueueEntryByUser(user);
        queueRepository.delete(queueEntry);

        // Update the positions of the users in the queue
        List<User> usersInQueue = userRepository.getUsersInQueue();
        for (User u : usersInQueue) {
            int newPositionInQueue = u.getPositionInQueue() - 1;
            u.setPositionInQueue(newPositionInQueue);
            userRepository.save(u);
        }
    }

}

