package com.kakaopaystocks.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "stocks", indexes = {
    @Index(name = "idx_view_count", columnList = "viewCount"),
    @Index(name = "idx_price_change", columnList = "priceChange"),
    @Index(name = "idx_volume", columnList = "volume")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String code;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private Long viewCount;

    @Column(nullable = false)
    private BigDecimal priceChange;

    @Column(nullable = false)
    private Long volume;

    public Stock(String code, String name, BigDecimal price) {
        this.code = code;
        this.name = name;
        this.price = price;
        this.viewCount = 0L;
        this.priceChange = BigDecimal.ZERO;
        this.volume = 0L;
    }
}