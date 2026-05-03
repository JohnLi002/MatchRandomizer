package com.example.MatchRandomizer.data.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "conditions")
public class Tournament {
    @Id
    @Column(name = "ID")
    @GeneratedValue
    private int id;

    @Column(name = "Max_Player_Count")
    private String Max_Player_Count;

    @Column(name = "Name")
    private String name;

    public Tournament(String name){
        this.name=name;
    }

    public String getMax_Player_Count() {
        return Max_Player_Count;
    }

    public void setMax_Player_Count(String max_Player_Count) {
        Max_Player_Count = max_Player_Count;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
