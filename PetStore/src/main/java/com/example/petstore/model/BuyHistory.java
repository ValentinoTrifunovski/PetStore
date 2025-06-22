package com.example.petstore.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
public class BuyHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate dateOfExecution;

    private int successfulBuys;
    private int failedBuys;

    public BuyHistory() {
    }

    public BuyHistory(LocalDate dateOfExecution, int successfulBuys, int failedBuys) {
        this.dateOfExecution = dateOfExecution;
        this.successfulBuys = successfulBuys;
        this.failedBuys = failedBuys;
    }

}
