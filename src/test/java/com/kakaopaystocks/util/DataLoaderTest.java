package com.kakaopaystocks.util;

import com.kakaopaystocks.entity.Stock;
import com.kakaopaystocks.repository.StockRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DataLoaderTest {

    @Mock
    private StockRepository stockRepository;

    @InjectMocks
    private DataLoader dataLoader;

    @Test
    void run_WithEmptyRepository_ShouldLoadStocks() throws Exception {
        when(stockRepository.count()).thenReturn(0L);
        List<Stock> mockStocks = Arrays.asList(new Stock(), new Stock());

        dataLoader.run();

        verify(stockRepository).count();
        verify(stockRepository).saveAll(anyList());
    }

    @Test
    void run_WithNonEmptyRepository_ShouldNotLoadStocks() throws Exception {
        when(stockRepository.count()).thenReturn(10L);

        dataLoader.run();

        verify(stockRepository).count();
        verify(stockRepository, never()).saveAll(anyList());
    }
}