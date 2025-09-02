package com.kakaopaystocks.service.impl;

import com.kakaopaystocks.dto.StockDTO;
import com.kakaopaystocks.dto.StockMapper;
import com.kakaopaystocks.dto.UpdateResult;
import com.kakaopaystocks.entity.Stock;
import com.kakaopaystocks.repository.StockRepository;
import com.kakaopaystocks.service.StockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

@Service
public class StockServiceImpl implements StockService {
    private final StockRepository stockRepository;
    private final StockMapper stockMapper;
    private final Random random = new Random();
    private static final Logger logger = LoggerFactory.getLogger(StockServiceImpl.class);

    public StockServiceImpl(StockRepository stockRepository, StockMapper stockMapper) {
        this.stockRepository = stockRepository;
        this.stockMapper = stockMapper;
    }

    @Override
    public Page<StockDTO> getStocksByTag(String tag, Pageable pageable) {
        Page<Stock> stocks = switch (tag) {
            case "popular" -> stockRepository.findAllByOrderByViewCountDesc(pageable);
            case "rising" -> stockRepository.findAllByOrderByPriceChangeDesc(pageable);
            case "falling" -> stockRepository.findAllByOrderByPriceChangeAsc(pageable);
            case "volume" -> stockRepository.findAllByOrderByVolumeDesc(pageable);
            default -> throw new IllegalArgumentException("Invalid tag: " + tag);
        };
        return stocks.map(stockMapper::toDto);
    }

    @Override
    @Transactional
    public UpdateResult updateRandomData() {
        List<Stock> stocks = stockRepository.findAll();

        long updatedCount = stocks.stream()
                .map(this::updateStock)
                .filter(Boolean::booleanValue)
                .count();

        boolean success = updatedCount == stocks.size();

        String message;
        if (updatedCount == 0) {
            message = "No stocks were updated.";
        } else if (updatedCount == stocks.size()) {
            message = String.format("Successfully updated all %d stocks.", updatedCount);
        } else {
            message = String.format("Partially updated stocks. %d out of %d updated.", updatedCount, stocks.size());
        }

        return new UpdateResult(success, (int) updatedCount, message);
    }

    private boolean updateStock(Stock stock) {
        try {
            stock.setViewCount(stock.getViewCount() + random.nextInt(100));
            stock.setPriceChange(BigDecimal.valueOf(random.nextDouble() * 10 - 5));
            stock.setVolume(stock.getVolume() + random.nextInt(10000));
            stockRepository.save(stock);
            return true;
        } catch (Exception e) {
            logger.error("Failed to update stock: {}", stock.getCode(), e);
            return false;
        }
    }
}