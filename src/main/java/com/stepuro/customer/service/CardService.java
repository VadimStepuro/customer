package com.stepuro.customer.service;

import com.stepuro.customer.api.dto.CardDto;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface CardService {
    List<CardDto> findAll();

    CardDto findById(UUID id);

    CardDto findByCardNumber(String cardNumber);

    boolean existsByCardNumber(String cardNumber);

    boolean checkCardOwner(String cardNumber, Integer individualId);

    boolean validateCardBalance(String cardNumber, BigDecimal amount);

    CardDto create(CardDto cardDto);

    CardDto edit(CardDto cardDto);

    void delete(UUID id);
}
