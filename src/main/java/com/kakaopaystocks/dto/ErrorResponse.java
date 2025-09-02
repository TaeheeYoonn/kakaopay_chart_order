package com.kakaopaystocks.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ErrorResponse {
    // Getters
    private final String message;
    private final String error;
    private final String path;
    private final LocalDateTime timestamp;

    public ErrorResponse(String message, String error, String path, LocalDateTime now) {
        this.message = message;
        this.error = error;
        this.path = path;
        this.timestamp = LocalDateTime.now();
    }

}
