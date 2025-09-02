package com.kakaopaystocks.controller;

import com.kakaopaystocks.dto.StockDTO;
import com.kakaopaystocks.dto.UpdateResult;
import com.kakaopaystocks.service.StockService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StockController.class)
public class StockControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StockService stockService;

    private Page<StockDTO> samplePage;

    @BeforeEach
    void setUp() {
        StockDTO sampleStock = new StockDTO("005930", "삼성전자", new BigDecimal("61500"), 0L, BigDecimal.ZERO, 0L);
        sampleStock.setViewCount(1000L);
        sampleStock.setPriceChange(new BigDecimal("500"));
        sampleStock.setVolume(1000000L);

        samplePage = new PageImpl<>(Arrays.asList(sampleStock));

    }

    @Test
    void getStocksByTag_ShouldReturnStocksPage() throws Exception {
        when(stockService.getStocksByTag(eq("popular"), any(Pageable.class))).thenReturn(samplePage);

        mockMvc.perform(get("/api/stocks/popular")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].code").value("005930"))
                .andExpect(jsonPath("$.content[0].name").value("삼성전자"))
                .andExpect(jsonPath("$.content[0].price").value(61500))
                .andExpect(jsonPath("$.totalElements").value(1));
    }

    @Test
    void updateRandomData_ShouldReturnUpdateResult() throws Exception {
        UpdateResult updateResult = new UpdateResult(true, 100, "Successfully updated all 100 stocks.");
        when(stockService.updateRandomData()).thenReturn(updateResult);

        mockMvc.perform(post("/api/stocks/update-random"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.updatedCount").value(100))
                .andExpect(jsonPath("$.message").value("Successfully updated all 100 stocks."));
    }
}