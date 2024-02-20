package com.stepuro.customer.service.samples;

import com.stepuro.customer.api.dto.CardDto;
import com.stepuro.customer.api.dto.mapper.CardMapper;
import com.stepuro.customer.model.Card;
import com.stepuro.customer.model.enums.CardStatus;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class CardSamples {
    public static Card card1 = Card.builder()
            .id(UUID.randomUUID())
            .accountNumber("IE12BOFI90000112345678")
            .cardNumber("5425233430109903")
            .status(CardStatus.ACTIVE)
            .createdDate(Timestamp.valueOf(LocalDateTime.now()))
            .updatedDate(Timestamp.valueOf(LocalDateTime.now()))
            .expiryDate(Date.valueOf(LocalDate.now().plusYears(3)))
            .build();
    public static Card card2 = Card.builder()
            .id(UUID.randomUUID())
            .accountNumber("IE12BOFI90000112345678")
            .cardNumber("5425233430109903")
            .status(CardStatus.ACTIVE)
            .createdDate(Timestamp.valueOf(LocalDateTime.now()))
            .updatedDate(Timestamp.valueOf(LocalDateTime.now()))
            .expiryDate(Date.valueOf(LocalDate.now().plusYears(3)))
            .build();
    public static Card card3 = Card.builder()
            .id(UUID.randomUUID())
            .accountNumber("IE12BOFI90000112345678")
            .cardNumber("5425233430109903")
            .status(CardStatus.ACTIVE)
            .createdDate(Timestamp.valueOf(LocalDateTime.now()))
            .updatedDate(Timestamp.valueOf(LocalDateTime.now()))
            .expiryDate(Date.valueOf(LocalDate.now().plusYears(3)))
            .build();
    public static Card card4 = Card.builder()
            .id(UUID.randomUUID())
            .accountNumber("IE12BOFI90000112345678")
            .cardNumber("5425233430109903")
            .status(CardStatus.ACTIVE)
            .createdDate(Timestamp.valueOf(LocalDateTime.now()))
            .updatedDate(Timestamp.valueOf(LocalDateTime.now()))
            .expiryDate(Date.valueOf(LocalDate.now().plusYears(3)))
            .build();

    public static CardDto cardDto = CardMapper.INSTANCE.cardToCardDto(card1);
}
