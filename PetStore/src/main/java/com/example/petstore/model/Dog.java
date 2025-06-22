package com.example.petstore.model;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Dog extends Pet {

    private Integer rating;

    public int getPrice() {
        return getAge(getDateOfBirth()) + rating;
    }

}
