package com.kakaopaystocks.dto;

import lombok.Getter;

@Getter
public class UpdateResult {
    private final boolean success;
    private final int updatedCount;
    private final String message;

    public UpdateResult(boolean success, int updatedCount, String message) {
        this.success = success;
        this.updatedCount = updatedCount;
        this.message = message;
    }

}
