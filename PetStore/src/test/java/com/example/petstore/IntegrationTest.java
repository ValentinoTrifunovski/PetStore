package com.example.petstore;

import com.example.petstore.model.Pet;
import com.example.petstore.model.User;
import com.example.petstore.repository.BuyHistoryRepository;
import com.example.petstore.repository.PetRepository;
import com.example.petstore.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;


import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
class IntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private BuyHistoryRepository buyHistoryRepository;

    @Test
    void testCreateUsersEndpoint() throws Exception {
        mockMvc.perform(post("/api/users/create"))
                .andExpect(status().isOk());

        List<User> users = userRepository.findAll();
        assertFalse(users.isEmpty());
        assertTrue(users.size() >= 10);
    }

    @Test
    void testCreatePetsEndpoint() throws Exception {
        mockMvc.perform(post("/api/pets/create"))
                .andExpect(status().isOk());

        List<Pet> pets = petRepository.findAll();
        assertFalse(pets.isEmpty());
        assertTrue(pets.size() >= 20);
    }

    @Test
    void BuyPetsEndpoint() throws Exception {
        mockMvc.perform(post("/api/users/create")).andExpect(status().isOk());
        mockMvc.perform(post("/api/pets/create")).andExpect(status().isOk());

        mockMvc.perform(post("/api/users/buy"))
                .andExpect(status().isOk());

        List<Pet> ownedPets = petRepository.findAll().stream()
                .filter(p -> p.getOwner() != null)
                .toList();

        assertFalse(ownedPets.isEmpty());

        assertFalse(buyHistoryRepository.findAll().isEmpty());
    }

}
