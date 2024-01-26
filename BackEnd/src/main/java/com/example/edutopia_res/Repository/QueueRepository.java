package com.example.edutopia_res.Repository;

import com.example.edutopia_res.entities.QueueEntry;
import com.example.edutopia_res.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface QueueRepository extends JpaRepository<QueueEntry, Integer> {

    @Query("Select count (a) From QueueEntry a ")
    int getQueueSize();

    @Query("Select a From QueueEntry a where a.user = :u")

    QueueEntry getQueueEntryByUser(@Param("u") User user);



}
