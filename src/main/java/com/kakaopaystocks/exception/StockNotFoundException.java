package com.kakaopaystocks.exception;

import lombok.Getter;

@Getter
public class StockNotFoundException extends RuntimeException {
    private final String stockId;

    public StockNotFoundException(String stockId) {
        super("Stock not found with id: " + stockId);
        this.stockId = stockId;
    }

}
