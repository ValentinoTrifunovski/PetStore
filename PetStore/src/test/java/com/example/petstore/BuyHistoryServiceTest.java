package com.example.petstore;

import com.example.petstore.model.BuyHistory;
import com.example.petstore.repository.BuyHistoryRepository;
import com.example.petstore.service.BuyHistoryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BuyHistoryServiceTest {

    @Mock
    private BuyHistoryRepository buyHistoryRepository;

    @InjectMocks
    private BuyHistoryService buyHistoryService;

    @Test
    void testGetAllBuyHistories_returnsList() {
        List<BuyHistory> histories = List.of(new BuyHistory(), new BuyHistory());
        when(buyHistoryRepository.findAll()).thenReturn(histories);

        List<BuyHistory> allHistories = buyHistoryService.findAll();

        assertEquals(2, allHistories.size());
        verify(buyHistoryRepository).findAll();
    }
}
