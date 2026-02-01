package com.example.Randomizer3.data.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "conditions")
public class Conditions {
    @Id
    @Column(name = "ID")
    @GeneratedValue
    private int id;

    @Column(name = "Unique_Effect")
    private String effect;
}
