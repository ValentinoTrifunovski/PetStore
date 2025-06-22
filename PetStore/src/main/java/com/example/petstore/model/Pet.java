package com.example.petstore.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Setter
@Getter
@Entity
public abstract class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User owner;
    private String name;
    private String description;
    private Date dateOfBirth;


    public int getAge(Date dateOfBirth) {
        LocalDate currentDate = LocalDate.now();
        return currentDate.getYear() - dateOfBirth.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().getYear();
    }

    @Transient
    @JsonProperty
    public abstract int getPrice();

}
