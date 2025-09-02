package com.kakaopaystocks.util;

import com.kakaopaystocks.entity.Stock;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CsvReaderUtilTest {

    @Test
    void readStocksFromCsv_ShouldReadCorrectly() throws IOException {
        // 테스트용 CSV 파일 생성
        String testCsvContent = "id,code,name,price\n1,005930,삼성전자,61500\n2,373220,LG에너지솔루션,452000";
        Files.write(new ClassPathResource("stocks.csv").getFile().toPath(), testCsvContent.getBytes(StandardCharsets.UTF_8));

        List<Stock> stocks = CsvReaderUtil.readStocksFromCsv("stocks.csv");

        assertEquals(2, stocks.size());
        assertEquals("005930", stocks.get(0).getCode());
        assertEquals("삼성전자", stocks.get(0).getName());
        assertEquals(new BigDecimal("61500"), stocks.get(0).getPrice());
        assertEquals("373220", stocks.get(1).getCode());
        assertEquals("LG에너지솔루션", stocks.get(1).getName());
        assertEquals(new BigDecimal("452000"), stocks.get(1).getPrice());
    }

    @Test
    void readStocksFromCsv_WithInvalidFile_ShouldThrowException() {
        assertThrows(RuntimeException.class, () -> {
            CsvReaderUtil.readStocksFromCsv("non-existent-file.csv");
        });
    }
}