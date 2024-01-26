package com.example.edutopia_res.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Table;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "QueueStatus ")
public class QueueStatus {
    private int currentQueueSize;
    private int estimatedWaitTimeInMinutes;
    private int currentWaitTimeInMinutes;
    private List<User> usersInQueue;
    private List<Dish> dishesBeingPrepared;
}
