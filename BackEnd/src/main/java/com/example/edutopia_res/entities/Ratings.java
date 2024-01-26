package com.example.edutopia_res.entities;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ratings")

public class Ratings implements Serializable {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "rate_id")
        private int rateId;

    private   double score;

        @Temporal(TemporalType.TIMESTAMP)
        @Column(name = "created_at")
        private  Date createdAt;
        @ManyToOne(cascade = CascadeType.ALL)
        private   User user;
    @ManyToOne(cascade = CascadeType.ALL)
    private Dish dish;

    }

