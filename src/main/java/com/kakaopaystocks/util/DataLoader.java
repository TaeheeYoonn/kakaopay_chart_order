package com.kakaopaystocks.util;

import com.kakaopaystocks.controller.StockController;
import com.kakaopaystocks.entity.Stock;
import com.kakaopaystocks.repository.StockRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class DataLoader implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(StockController.class);
    private final StockRepository stockRepository;

    public DataLoader(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    @Override
    public void run(String... args) {
        if (stockRepository.count() == 0) {
            List<Stock> stocks = CsvReaderUtil.readStocksFromCsv("stocks.csv");
            stockRepository.saveAll(stocks);
            System.out.println("Loaded " + stocks.size() + " stocks from CSV file.");
        }
    }
}
