package com.example.petstore.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class PetDTO {
    private String type; // "cat" or "dog"
    private String name;
    private String description;
    private Date dateOfBirth;
    private Integer rating;

}
