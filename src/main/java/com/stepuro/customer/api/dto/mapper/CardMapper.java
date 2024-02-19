package com.stepuro.customer.api.dto.mapper;

import com.stepuro.customer.api.dto.CardDto;
import com.stepuro.customer.model.Card;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CardMapper {
    CardMapper INSTANCE = Mappers.getMapper(CardMapper.class);

    @Mapping(source = "individualDto", target = "individual")
    Card cardDtoToCard(CardDto cardDto);

    @Mapping(source = "individual", target = "individualDto")
    CardDto cardToCardDto(Card card);
}
