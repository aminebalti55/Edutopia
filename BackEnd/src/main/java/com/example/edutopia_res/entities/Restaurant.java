package com.example.edutopia_res.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity

@Table(name = "restaurant")
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idRestaurant;

    private String restaurantName;

    private int capacity;


    @OneToMany(cascade = CascadeType.ALL, mappedBy="restaurant")
    private Set<Subscription> subscriptions;

    @OneToOne(cascade = CascadeType.ALL)
    private Menu menu;


}
