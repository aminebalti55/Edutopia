package com.example.edutopia_res.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity

@Table(name = "Report")
public class Report {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "report_id")
        private int reportId;

        private String title;

        private String note;

        private boolean urgent;


        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "dish_id")
        private Dish dish;

        @Temporal(TemporalType.TIMESTAMP)
        @Column(name = "created_at")
        private Date createdAt;

        @Temporal(TemporalType.TIMESTAMP)
        @Column(name = "closed_at")
        private Date closedAt;

        private boolean traitee ;

        @ManyToOne(cascade = CascadeType.ALL)
        private User user;

    }


