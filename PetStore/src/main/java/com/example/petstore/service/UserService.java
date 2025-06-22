package com.example.petstore.service;

import com.example.petstore.model.*;
import com.example.petstore.repository.BuyHistoryRepository;
import com.example.petstore.repository.PetRepository;
import com.example.petstore.repository.UserRepository;
import com.github.javafaker.Faker;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PetRepository petRepository;
    private final BuyHistoryRepository buyHistoryRepository;
    private final Faker faker = new Faker();

    public UserService(UserRepository userRepository, PetRepository petRepository, BuyHistoryRepository buyHistoryRepository, BuyHistoryService buyHistoryService) {
        this.userRepository = userRepository;
        this.petRepository = petRepository;
        this.buyHistoryRepository = buyHistoryRepository;
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }
    public List<User> listUsers() {
        return userRepository.findAll();
    }

    public void buyPet(Long petId, Long userId) {
        Pet pet = petRepository.findById(petId)
                .orElseThrow(() -> new ResourceNotFoundException("Pet not found"));

        if (pet.getOwner() != null) {
            throw new RuntimeException("Pet already has an owner.");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));


        int price = pet.getPrice();

        if (user.getBudget() < price) {
            throw new RuntimeException("Insufficient funds");
        }

        user.setBudget(user.getBudget() - price);
        pet.setOwner(user);

        userRepository.save(user);
        petRepository.save(pet);

        if (pet instanceof Cat) {
            System.out.printf("Meow, cat %s has owner %s%n", pet.getName(), user.getFirstName());
        } else if (pet instanceof Dog) {
            System.out.printf("Woof, dog %s has owner %s%n", pet.getName(), user.getFirstName());
        }
    }

    public List<User> createRandomUsers() {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            User user = new User();
            user.setFirstName(faker.name().firstName());
            user.setLastName(faker.name().lastName());
            user.setEmail(faker.internet().emailAddress());
            user.setBudget(faker.number().numberBetween(5, 100));
            users.add(userRepository.save(user));
        }
        return users;
    }
    public void buyPetsForAllUsers() {
        List<User> users = userRepository.findAll();
        List<Pet> availablePets = petRepository.findAll().stream().filter(p -> p.getOwner() == null).collect(Collectors.toList());
        List<Pet> toRemove = new ArrayList<>();
        int buyCounter = 0,failedBuyCounter = 0;
        for (User user : users) {
            for (Pet pet : availablePets) {
                int price = pet.getPrice();
                if (user.getBudget() < price) {
                    System.out.println("Insufficient funds");
                    failedBuyCounter++;
                } else{
                    user.setBudget(user.getBudget() - price);
                    pet.setOwner(user);
                    userRepository.save(user);
                    petRepository.save(pet);
                    toRemove.add(pet);

                    if(pet instanceof Cat) {
                        System.out.printf("Meow, cat %s has owner %s%n", pet.getName(), user.getFirstName());
                    } else if(pet instanceof Dog) {
                        System.out.printf("Woof, dog %s has owner %s%n", pet.getName(), user.getFirstName());
                    }

                    buyCounter++;
                }

            }
            availablePets.removeAll(toRemove);
        }
        BuyHistory buyHistory = new BuyHistory(LocalDate.now(),buyCounter,failedBuyCounter);
        buyHistoryRepository.save(buyHistory);
    }

}
