package com.stepuro.customer.service;

import com.stepuro.customer.api.dto.LegalEntityDto;

import java.util.List;

public interface LegalEntityService {
    List<LegalEntityDto> findAll();

    LegalEntityDto findById(Integer id);

    LegalEntityDto create(LegalEntityDto legalEntityDto);

    LegalEntityDto edit(LegalEntityDto legalEntityDto);

    void delete(Integer id);
}
