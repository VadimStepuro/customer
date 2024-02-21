package com.stepuro.customer.service;

import com.stepuro.customer.api.dto.CardDto;

import java.util.List;
import java.util.UUID;

public interface CardService {
    List<CardDto> findAll();

    CardDto findById(UUID id);

    CardDto create(CardDto cardDto);

    CardDto edit(CardDto cardDto);

    void delete(UUID id);
}
