package com.kakaopaystocks.service;

import com.kakaopaystocks.dto.StockDTO;
import com.kakaopaystocks.dto.UpdateResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StockService {
    Page<StockDTO> getStocksByTag(String tag, Pageable pageable);
    UpdateResult updateRandomData();
    //void updateRandomData();
}
