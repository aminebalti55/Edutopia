package com.example.edutopia_res.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Subscription")
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subscription_id")
    private int subscriptionId;

    private int duration;


    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private Date createdAt;


    @Enumerated(EnumType.STRING)
    private PackageType packageType;

    private BigDecimal price;


    @Column(name = "perks")
    private String perks;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @OneToOne(fetch = FetchType.LAZY)
    private User user;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Payment payment;
    @ManyToOne(cascade = CascadeType.ALL)
    private Restaurant restaurant;

    private String qrCode;



    public enum PackageType {
        FOODIE,
        BIG_FOODIE,
        ULTRA_FOODIE
    }

    private String preferredDish;

}
