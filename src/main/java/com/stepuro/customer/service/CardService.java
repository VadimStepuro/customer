package com.stepuro.customer.service;

import com.stepuro.customer.api.dto.CardDto;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.UUID;

public interface CardService {
    List<CardDto> findAll();

    CardDto findById(UUID id);

    @Transactional
    CardDto create(CardDto cardDto);

    @Transactional
    CardDto edit(CardDto cardDto);

    @Transactional
    void delete(UUID id);
}
