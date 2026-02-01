package com.example.Randomizer3;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
public class Form {

    private long id;

    @NotNull
    @Size(min=1)
    private String name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
