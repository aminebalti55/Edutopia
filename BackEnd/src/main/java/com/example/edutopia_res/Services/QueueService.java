/*package com.example.edutopia_res.Services;

import com.example.edutopia_res.Iservices.IqueueService;
import com.example.edutopia_res.Repository.QueueRepository;
import com.example.edutopia_res.Repository.RestaurantRepository;
import com.example.edutopia_res.entities.Queue;
import com.example.edutopia_res.entities.QueueEntry;
import com.example.edutopia_res.entities.Restaurant;
import com.example.edutopia_res.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

@Service
public class QueueService  implements IqueueService {
    @Autowired

  QueueRepository queueRepository;
@Autowired
    RestaurantRepository restaurantRepository;


    public void addUserToQueue(int queueId, User user) {
        Queue queue = getQueueById(queueId);
        QueueEntry queueEntry = new QueueEntry();

        queueEntry.setQueue(queue);
        queue.getQueueEntries().add(queueEntry);
        queue.setQueueSize(queue.getQueueSize() + 1);
        queue.setWaitTime(calculateEstimatedWaitTime(queue));
        queueRepository.save(queue);
    }

    public int calculateEstimatedWaitTime(Queue queue) {
        // Calculate the estimated wait time based on the current queue size and restaurant capacity
        int restaurantCapacity = queue.getRestaurant().getCapacity();
        int queueSize = queue.getQueueSize();
        return (queueSize / restaurantCapacity) * 15; // Assume each table takes 15 minutes to turn over
    }
    public List<Queue> getAllQueues() {
        return queueRepository.findAll();
    }

    public Queue getQueueById(int queueId) {
        return queueRepository.findById(queueId).orElseThrow(() -> new RuntimeException("Queue not found"));
    }

    public Queue createQueue(Queue queue) {
        return queueRepository.save(queue);
    }

    public void deleteQueue(int queueId) {
        queueRepository.deleteById(queueId);
    }
}
*/





