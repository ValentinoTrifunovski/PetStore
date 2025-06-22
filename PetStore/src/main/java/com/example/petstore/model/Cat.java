package com.example.petstore.model;

import jakarta.persistence.Entity;

@Entity
public class Cat extends Pet{

    @Override
    public int getPrice() {
        return getAge(getDateOfBirth());
    }

}
