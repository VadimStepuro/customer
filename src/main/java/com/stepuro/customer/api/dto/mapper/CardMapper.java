package com.stepuro.customer.api.dto.mapper;

import com.stepuro.customer.api.dto.CardDto;
import com.stepuro.customer.model.Card;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CardMapper {
    CardMapper INSTANCE = Mappers.getMapper(CardMapper.class);

    Card cardDtoToCard(CardDto cardDto);

    CardDto cardToCardDto(Card card);
}
