package com.kakaopaystocks.service.impl;

import com.kakaopaystocks.dto.StockDTO;
import com.kakaopaystocks.dto.StockMapper;
import com.kakaopaystocks.dto.UpdateResult;
import com.kakaopaystocks.entity.Stock;
import com.kakaopaystocks.repository.StockRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StockServiceImplTest {

    @Mock
    private StockRepository stockRepository;

    @Mock
    private StockMapper stockMapper;

    @InjectMocks
    private StockServiceImpl stockService;

    private Stock sampleStock;
    private StockDTO sampleStockDTO;
    private Page<Stock> samplePage;

    @BeforeEach
    void setUp() {
        sampleStock = new Stock("005930", "Samsung Electronics", new BigDecimal("67000"));
        sampleStock.setViewCount(1000L);
        sampleStock.setPriceChange(new BigDecimal("500"));
        sampleStock.setVolume(1000000L);

        sampleStockDTO = new StockDTO("005930", "Samsung Electronics", new BigDecimal("67000"), 1000L, new BigDecimal("500"), 1000000L);

        samplePage = new PageImpl<>(Arrays.asList(sampleStock));
    }

    @Test
    void getStocksByTag_Popular_ShouldReturnOrderedByViewCount() {
        Pageable pageable = PageRequest.of(0, 10);
        when(stockRepository.findAllByOrderByViewCountDesc(pageable)).thenReturn(samplePage);
        when(stockMapper.toDto(any(Stock.class))).thenReturn(sampleStockDTO);

        Page<StockDTO> result = stockService.getStocksByTag("popular", pageable);

        assertEquals(1, result.getContent().size());
        assertEquals("005930", result.getContent().get(0).getCode());
        verify(stockRepository).findAllByOrderByViewCountDesc(pageable);
        verify(stockMapper).toDto(any(Stock.class));
    }

    @Test
    void getStocksByTag_InvalidTag_ShouldThrowException() {
        Pageable pageable = PageRequest.of(0, 10);

        assertThrows(IllegalArgumentException.class, () -> {
            stockService.getStocksByTag("invalid", pageable);
        });
    }

    @Test
    void updateRandomData_AllSuccessful_ShouldReturnCorrectResult() {
        List<Stock> stocks = Arrays.asList(sampleStock, sampleStock);
        when(stockRepository.findAll()).thenReturn(stocks);
        when(stockRepository.save(any(Stock.class))).thenReturn(sampleStock);

        UpdateResult result = stockService.updateRandomData();

        assertTrue(result.isSuccess());
        assertEquals(2, result.getUpdatedCount());
        assertEquals("Successfully updated all 2 stocks.", result.getMessage());
        verify(stockRepository, times(2)).save(any(Stock.class));
    }
}