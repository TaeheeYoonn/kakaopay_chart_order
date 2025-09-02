package com.kakaopaystocks.util;

import com.kakaopaystocks.entity.Stock;
import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class CsvReaderUtil {

    public static List<Stock> readStocksFromCsv(String fileName) {
        List<Stock> stocks = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new ClassPathResource(fileName).getInputStream(), StandardCharsets.UTF_8))) {
            // Skip the header
            String line = br.readLine();
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                stocks.add(new Stock(
                        values[1], // code
                        values[2], // name
                        new BigDecimal(values[3].replace(",", "")) // price
                ));
            }
        } catch (Exception e) {
            throw new RuntimeException("Error reading CSV file: " + e.getMessage(), e);
        }
        return stocks;
    }
}
