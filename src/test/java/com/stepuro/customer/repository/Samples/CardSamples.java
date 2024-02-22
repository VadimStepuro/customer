package com.stepuro.customer.repository.Samples;

import com.stepuro.customer.model.Card;
import com.stepuro.customer.model.enums.CardStatus;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;

public class CardSamples {
    public static Card card1 = Card.builder()
            .accountNumber("IE12BOFI90000112345678")
            .cardNumber("5425233430109933")
            .status(CardStatus.ACTIVE)
            .createdDate(Timestamp.from(Instant.now()))
            .updatedDate(Timestamp.from(Instant.now()))
            .expiryDate(Date.valueOf(LocalDate.now().plusYears(3)))
            .balance(new BigDecimal("300.00"))
            .build();
    public static Card card2 = Card.builder()
            .accountNumber("IE12BOFI90000112345644")
            .cardNumber("5425233430109944")
            .status(CardStatus.ACTIVE)
            .createdDate(Timestamp.from(Instant.now()))
            .updatedDate(Timestamp.from(Instant.now()))
            .expiryDate(Date.valueOf(LocalDate.now().plusYears(3)))
            .balance(new BigDecimal("400.00"))
            .build();
    public static Card card3 = Card.builder()
            .accountNumber("IE12BOFI90000112345655")
            .cardNumber("5425233430109955")
            .status(CardStatus.ACTIVE)
            .createdDate(Timestamp.from(Instant.now()))
            .updatedDate(Timestamp.from(Instant.now()))
            .expiryDate(java.sql.Date.valueOf(LocalDate.now().plusYears(3)))
            .balance(new BigDecimal("350.00"))
            .build();
    public static Card card4 = Card.builder()
            .accountNumber("IE12BOFI90000112345666")
            .cardNumber("5425233430109966")
            .status(CardStatus.ACTIVE)
            .createdDate(Timestamp.from(Instant.now()))
            .updatedDate(Timestamp.from(Instant.now()))
            .expiryDate(Date.valueOf(LocalDate.now().plusYears(3)))
            .balance(new BigDecimal("200.00"))
            .build();

}
