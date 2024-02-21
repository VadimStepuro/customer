package com.stepuro.customer.service;

import com.stepuro.customer.api.dto.IndividualDto;
import jakarta.transaction.Transactional;

import java.util.List;

public interface IndividualService {
    List<IndividualDto> findAll();

    IndividualDto findById(Integer id);

    @Transactional
    IndividualDto create(IndividualDto individualDto);

    @Transactional
    IndividualDto edit(IndividualDto individualDto);

    @Transactional
    void delete(Integer id);
}
