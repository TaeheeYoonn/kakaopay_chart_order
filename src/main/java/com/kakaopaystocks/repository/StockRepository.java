package com.kakaopaystocks.repository;

import com.kakaopaystocks.entity.Stock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {
    Page<Stock> findAllByOrderByViewCountDesc(Pageable pageable);
    Page<Stock> findAllByOrderByPriceChangeDesc(Pageable pageable);
    Page<Stock> findAllByOrderByPriceChangeAsc(Pageable pageable);
    Page<Stock> findAllByOrderByVolumeDesc(Pageable pageable);
}
