package com.kakaopaystocks.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class StockDTO {
    private String code;
    private String name;
    private BigDecimal price;
    private Long viewCount;
    private BigDecimal priceChange;
    private Long volume;

    // Constructor
    public StockDTO(String code, String name, BigDecimal price, Long viewCount, BigDecimal priceChange, Long volume) {
        this.code = code;
        this.name = name;
        this.price = price;
        this.viewCount = viewCount;
        this.priceChange = priceChange;
        this.volume = volume;
    }
}
