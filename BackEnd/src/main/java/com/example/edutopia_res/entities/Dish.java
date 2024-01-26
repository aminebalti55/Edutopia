package com.example.edutopia_res.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Dish {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dish_id")
    private int dishId;

  private String name;

    private String description;

    private float price;

    private double score;

    @Lob
    private  String image;
  private byte[] DishimageData; // binary data for profile picture

    @Enumerated(EnumType.STRING)
    private SpicinessLevel spicinessLevel;

    @Column(name = "category")
    @Enumerated(EnumType.STRING)
    private Category category;


    private boolean archived;
  @JsonIgnore
    @OneToMany(mappedBy = "dish", cascade = CascadeType.ALL)
    private List<Order> orders = new ArrayList<>();
  @JsonIgnore

    @OneToMany(cascade = CascadeType.ALL, mappedBy="dish")
    private List<Ratings> ratings;
  @JsonIgnore
    @OneToMany(mappedBy = "dish", cascade = CascadeType.ALL)
    private Set<Report> reports = new HashSet<>();
  @JsonIgnore
  @ManyToOne(fetch = FetchType.LAZY )

  @JoinColumn(name = "menu_id")
  private Menu menu;
}
