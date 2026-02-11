package com.example.MatchRandomizer.data.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "environment")
public class Environment {
    @Id
    @Column(name = "ID")
    @GeneratedValue
    private int id;

    @Column(name = "area")
    private String area;


    public String getArea() {
        return area;
    }

    public void setArea(String a) {
        area = a;
    }
}
