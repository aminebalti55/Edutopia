package com.example.edutopia_res.Repository;

import com.example.edutopia_res.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findOneByEmailAndPassword(String email, String password);
    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
    User findByEmail(String email);
    User findByUsername(String username);
    @Query("SELECT u FROM User u WHERE u.queueEntry IS NOT NULL")
    List<User> getUsersInQueue();

}