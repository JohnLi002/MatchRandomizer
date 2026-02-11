package com.example.MatchRandomizer.data.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "people")
public class Person {
    @Id
    @Column(name = "ID")
    @GeneratedValue
    private int id;

    @Column(name = "Name")
    private String name;

    @Column(name = "Victories")
    private int victories;

    @Column(name = "Loses")
    private int loses;

    public Person(){
        victories = 0;
        loses = 0;
    }

    public int getID(){
        return id;
    }

    public void setID(int i){
        id = i;
    }

    public String getName(){
        return name;
    }

    public void setName(String s){
        name = s;
    }

    public int getVictories() {
        return victories;
    }

    public void setVictories(int i) {
        victories= i;
    }

    public int getLoses() {
        return loses;
    }

    public void setLoses(int i) {
        loses = i;
    }

    @Override
    public String toString(){
        return name;
    }
}
