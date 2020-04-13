package com.example.testapp.model;

public class Character {

    String name;

    public Character(String name, String image) {
        this.name = name;
        this.image = image;
    }

    String image;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
