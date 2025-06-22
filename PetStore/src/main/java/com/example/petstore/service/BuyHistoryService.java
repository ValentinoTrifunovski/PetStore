package com.example.petstore.service;

import com.example.petstore.model.BuyHistory;
import com.example.petstore.repository.BuyHistoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BuyHistoryService {
    private final BuyHistoryRepository buyHistoryRepository;

    public BuyHistoryService(BuyHistoryRepository buyHistoryRepository) {
        this.buyHistoryRepository = buyHistoryRepository;
    }

    public List<BuyHistory> findAll() {
        return buyHistoryRepository.findAll();
    }
}
