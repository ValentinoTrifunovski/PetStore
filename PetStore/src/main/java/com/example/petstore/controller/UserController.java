package com.example.petstore.controller;

import com.example.petstore.model.BuyHistory;
import com.example.petstore.model.User;
import com.example.petstore.service.BuyHistoryService;
import com.example.petstore.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping( "/api/users")
public class UserController {

    private final UserService userService;
    private final BuyHistoryService buyHistoryService;

    public UserController(UserService userService, BuyHistoryService buyHistoryService) {
        this.userService = userService;
        this.buyHistoryService = buyHistoryService;
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User createdUser = userService.createUser(user);
        return ResponseEntity.ok(createdUser);
    }


    @PostMapping("/{userId}/buy/{petId}")
    public ResponseEntity<String> buyPet(@PathVariable Long userId, @PathVariable Long petId) {
        try {
            userService.buyPet(petId, userId);
            return ResponseEntity.ok("Pet purchased successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<User>> listUsers() {
        return ResponseEntity.ok(userService.listUsers());
    }

    @PostMapping("/buy")
    public ResponseEntity<String> buyPetsForAllUsers() {
        userService.buyPetsForAllUsers();
        return ResponseEntity.ok("Buy command completed successfully!");
    }

    @PostMapping("/create")
    public ResponseEntity<List<User>> createRandomUsers() {
        return ResponseEntity.ok(userService.createRandomUsers());
    }

    @GetMapping("/history-log")
    public List<BuyHistory> getBuyHistory() {
        return buyHistoryService.findAll();
    }
}
