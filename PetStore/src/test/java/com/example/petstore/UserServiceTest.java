package com.example.petstore;

import com.example.petstore.model.Dog;
import com.example.petstore.model.User;
import com.example.petstore.repository.PetRepository;
import com.example.petstore.repository.UserRepository;
import com.example.petstore.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PetRepository petRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void testCreateUser() {
        User user = new User();
        user.setFirstName("Valentino");
        user.setBudget(100);
        userService.createUser(user);

        assertEquals("Valentino", user.getFirstName());
        assertEquals(100, user.getBudget());
    }

    @Test
    void testBuyPet_success() {
        User user = new User();
        user.setId(1000L);
        user.setFirstName("Valentino123");
        user.setBudget(100);
        Dog pet = new Dog();
        pet.setId(1000L);
        pet.setName("Claudio");
        pet.setDateOfBirth(new Date());
        pet.setRating(10);

        when(userRepository.findById(1000L)).thenReturn(Optional.of(user));
        when(petRepository.findById(1000L)).thenReturn(Optional.of(pet));

        userService.buyPet(1000L, 1000L);

        assertEquals(user, pet.getOwner());
        assertEquals("Claudio", pet.getName());
        assertEquals(10, pet.getRating());
        assertEquals(10, pet.getPrice());
        assertEquals(90, user.getBudget());
        verify(petRepository).save(pet);
    }
    @Test
    void testBuyPet_userNotFound() {
        Long userId = 1000L;
        Long petId = 2000L;


        assertThrows(ResourceNotFoundException.class, () -> {
            userService.buyPet(petId, userId);
        });

    }

    @Test
    void testBuyPet_petNotFound() {
        Long userId = 1000L;
        Long petId = 2000L;
        User user = new User();
        user.setId(userId);

        when(petRepository.findById(petId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            userService.buyPet(petId, userId);
        });

    }

    @Test
    void testBuyPet_petAlreadyHasOwner() {
        Long userId = 1000L;
        Long petId = 2000L;
        User user = new User();
        user.setId(userId);

        Dog dog = new Dog();
        dog.setId(petId);
        dog.setRating(10);
        dog.setDateOfBirth((new Date()));
        dog.setOwner(new User());

        when(petRepository.findById(petId)).thenReturn(Optional.of(dog));

        assertThrows(RuntimeException.class, () -> {
            userService.buyPet(petId, userId);
        });
    }

    @Test
    void testBuyPet_userHasInsufficientBudget() {
        Long userId = 1000L;
        Long petId = 2000L;
        User user = new User();
        user.setId(userId);
        user.setBudget(5);

        Dog dog = new Dog();
        dog.setId(petId);
        dog.setRating(10);
        dog.setDateOfBirth(new Date());
        dog.setOwner(null);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(petRepository.findById(petId)).thenReturn(Optional.of(dog));

        assertThrows(RuntimeException.class, () -> {
            userService.buyPet(petId, userId);
        });
    }
}
