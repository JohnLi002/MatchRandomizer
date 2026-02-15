package com.example.MatchRandomizer;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
public class Form {

    private int id;

    @NotNull
    @Size(min=1)
    private String name;

    private int victories;
    private int loses;

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

    public int getVictories() {
        return victories;
    }

    public void setVictories(int victories) {
        this.victories = victories;
    }

    public int getLoses() {
        return loses;
    }

    public void setLoses(int loses) {
        this.loses = loses;
    }
}
