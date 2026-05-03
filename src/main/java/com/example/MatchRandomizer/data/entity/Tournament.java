package com.example.MatchRandomizer.data.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "tournament")
public class Tournament {
    @Id
    @Column(name = "ID")
    @GeneratedValue
    private int id;

    @Column(name = "Max_Player_Count")
    private int max_players;

    @Column(name = "Name")
    private String name;

    public Tournament(){
        max_players = 0;
    }

    public Tournament(String name, int max_players){
        this.name=name;
        this.max_players = max_players;
    }

    public int getMax_players() {
        return max_players;
    }

    public void setMax_players(int max_players) {
        this.max_players = max_players;
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
