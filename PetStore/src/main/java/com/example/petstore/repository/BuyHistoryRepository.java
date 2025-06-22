package com.example.petstore.repository;

import com.example.petstore.model.BuyHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BuyHistoryRepository extends JpaRepository<BuyHistory, Long> {
}