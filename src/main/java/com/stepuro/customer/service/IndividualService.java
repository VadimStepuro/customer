package com.stepuro.customer.service;

import com.stepuro.customer.api.dto.IndividualDto;

import java.util.List;

public interface IndividualService {
    List<IndividualDto> findAll();

    IndividualDto findById(Integer id);

    IndividualDto create(IndividualDto individualDto);

    IndividualDto edit(IndividualDto individualDto);

    void delete(Integer id);
}
