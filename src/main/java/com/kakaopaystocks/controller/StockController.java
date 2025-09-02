package com.kakaopaystocks.controller;

import com.kakaopaystocks.dto.StockDTO;
import com.kakaopaystocks.dto.UpdateResult;
import com.kakaopaystocks.service.StockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/stocks")
public class StockController {
    private static final Logger logger = LoggerFactory.getLogger(StockController.class);
    private final StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @GetMapping("/{tag}")
    public ResponseEntity<Page<StockDTO>> getStocksByTag(@PathVariable("tag") String tag, Pageable pageable) {
            logger.info("Received pageable: size={}, page={}", pageable.getPageSize(), pageable.getPageNumber());
        return ResponseEntity.ok(stockService.getStocksByTag(tag, pageable));
    }

    @PostMapping("/update-random")
    public ResponseEntity<UpdateResult> updateRandomData() {
        UpdateResult result = stockService.updateRandomData();
        return ResponseEntity.ok(result);
    }
}
