package com.example.petstore.controller;

import com.example.petstore.dto.PetDTO;
import com.example.petstore.model.Cat;
import com.example.petstore.model.Dog;
import com.example.petstore.model.Pet;
import com.example.petstore.service.PetService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pets")
public class PetController {

    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    @PostMapping
    public ResponseEntity<Pet> createPet(@RequestBody PetDTO dto) {
        Pet pet;

        if (dto.getRating() != null) {
            Dog dog = new Dog();
            dog.setName(dto.getName());
            dog.setDescription(dto.getDescription());
            dog.setDateOfBirth(dto.getDateOfBirth());
            dog.setRating(dto.getRating());
            pet = dog;
        } else {
            Cat cat = new Cat();
            cat.setName(dto.getName());
            cat.setDescription(dto.getDescription());
            cat.setDateOfBirth(dto.getDateOfBirth());
            pet = cat;
        }
        return ResponseEntity.ok(petService.addPet(pet));
    }

    @GetMapping
    public ResponseEntity<List<Pet>> listPets() {
        return ResponseEntity.ok(petService.listPets());
    }

    @PostMapping("/create")
    public ResponseEntity<List<Pet>> createRandomPets() {
        return ResponseEntity.ok(petService.createRandomPets());
    }
}