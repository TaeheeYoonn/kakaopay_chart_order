package com.kakao.kakaotest.dto;

import com.kakao.kakaotest.entity.Stock;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-07-31T22:59:23+0900",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.12 (Amazon.com Inc.)"
)
@Component
public class StockMapperImpl implements StockMapper {

    @Override
    public StockDTO toDto(Stock stock) {
        if ( stock == null ) {
            return null;
        }

        String code = null;
        String name = null;
        BigDecimal price = null;
        Long viewCount = null;
        BigDecimal priceChange = null;
        Long volume = null;

        code = stock.getCode();
        name = stock.getName();
        price = stock.getPrice();
        viewCount = stock.getViewCount();
        priceChange = stock.getPriceChange();
        volume = stock.getVolume();

        StockDTO stockDTO = new StockDTO( code, name, price, viewCount, priceChange, volume );

        return stockDTO;
    }

    @Override
    public Stock toEntity(StockDTO stockDTO) {
        if ( stockDTO == null ) {
            return null;
        }

        Stock stock = new Stock();

        stock.setCode( stockDTO.getCode() );
        stock.setName( stockDTO.getName() );
        stock.setPrice( stockDTO.getPrice() );
        stock.setViewCount( stockDTO.getViewCount() );
        stock.setPriceChange( stockDTO.getPriceChange() );
        stock.setVolume( stockDTO.getVolume() );

        return stock;
    }

    @Override
    public List<StockDTO> toDtoList(List<Stock> stocks) {
        if ( stocks == null ) {
            return null;
        }

        List<StockDTO> list = new ArrayList<StockDTO>( stocks.size() );
        for ( Stock stock : stocks ) {
            list.add( toDto( stock ) );
        }

        return list;
    }
}
