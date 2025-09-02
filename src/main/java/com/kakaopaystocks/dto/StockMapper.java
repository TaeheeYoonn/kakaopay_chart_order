package com.kakaopaystocks.dto;

import com.kakaopaystocks.entity.Stock;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StockMapper {

    //@Mapping(target = "id", ignore = true)
    StockDTO toDto(Stock stock);

    @Mapping(target = "id", ignore = true)
    Stock toEntity(StockDTO stockDTO);

    List<StockDTO> toDtoList(List<Stock> stocks);
}