package com.example.petstore.service;

import com.example.petstore.model.Cat;
import com.example.petstore.model.Dog;
import com.example.petstore.model.Pet;
import com.example.petstore.repository.PetRepository;
import com.github.javafaker.Faker;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PetService {

    private final PetRepository petRepository;

    public PetService(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    public Pet addPet(Pet pet) {
        return petRepository.save(pet);
    }

    public List<Pet> listPets() {
        return petRepository.findAll();
    }

    public List<Pet> createRandomPets() {
        Faker faker = new Faker();
        List<Pet> pets = new ArrayList<Pet>();
        for (int i = 0; i < 20; i++) {
            boolean isCat = faker.bool().bool();
            if (isCat) {
                Cat cat = new Cat();
                cat.setName(faker.cat().name());
                cat.setDescription(cat.getName() + ": " + faker.cat().breed());
                cat.setDateOfBirth(faker.date().birthday());
                pets.add(cat);
                petRepository.save(cat);
            } else {
                Dog dog = new Dog();
                dog.setName(faker.dog().name());
                dog.setDescription(dog.getName() + ": " + faker.dog().breed());
                dog.setDateOfBirth(faker.date().birthday());
                dog.setRating(faker.number().numberBetween(1, 10));
                pets.add(dog);
                petRepository.save(dog);
            }
        }
        return pets;
    }
}

