package com.example.petstore;

import com.example.petstore.model.Cat;
import com.example.petstore.model.Pet;
import com.example.petstore.repository.PetRepository;
import com.example.petstore.service.PetService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PetServiceTest {

    @Mock
    private PetRepository petRepository;

    @InjectMocks
    private PetService petService;

    @Test
    void testCreatePet_success() {
        Cat cat = new Cat();
        cat.setName("Rex");
        cat.setDateOfBirth(new Date());


        petService.addPet(cat);

        assertNotNull(cat);
        assertEquals("Rex", cat.getName());
        assertEquals(0, cat.getPrice());
        verify(petRepository).save(cat);
    }


}

