package com.kakaopaystocks.exception;

public class InvalidStockDataException extends RuntimeException {
    public InvalidStockDataException(String message) {
        super(message);
    }
}