package com.stepuro.customer.repository.Samples;

import com.stepuro.customer.model.Card;
import com.stepuro.customer.model.enums.CardStatus;

import java.sql.Date;
import java.time.LocalDate;

public class CardSamples {
    public static Card card1 = Card.builder()
            .accountNumber("IE12BOFI90000112345678")
            .cardNumber("5425233430109903")
            .status(CardStatus.ACTIVE)
            .createdDate(Date.valueOf(LocalDate.now()))
            .updatedDate(Date.valueOf(LocalDate.now()))
            .expiryDate(Date.valueOf(LocalDate.now().plusYears(3)))
            .build();
    public static Card card2 = Card.builder()
            .accountNumber("IE12BOFI90000112345678")
            .cardNumber("5425233430109903")
            .status(CardStatus.ACTIVE)
            .createdDate(Date.valueOf(LocalDate.now()))
            .updatedDate(Date.valueOf(LocalDate.now()))
            .expiryDate(Date.valueOf(LocalDate.now().plusYears(3)))
            .build();
    public static Card card3 = Card.builder()
            .accountNumber("IE12BOFI90000112345678")
            .cardNumber("5425233430109903")
            .status(CardStatus.ACTIVE)
            .createdDate(Date.valueOf(LocalDate.now()))
            .updatedDate(Date.valueOf(LocalDate.now()))
            .expiryDate(Date.valueOf(LocalDate.now().plusYears(3)))
            .build();
    public static Card card4 = Card.builder()
            .accountNumber("IE12BOFI90000112345678")
            .cardNumber("5425233430109903")
            .status(CardStatus.ACTIVE)
            .createdDate(Date.valueOf(LocalDate.now()))
            .updatedDate(Date.valueOf(LocalDate.now()))
            .expiryDate(Date.valueOf(LocalDate.now().plusYears(3)))
            .build();

}
