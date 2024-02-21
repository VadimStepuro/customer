package com.stepuro.customer.service;

import com.stepuro.customer.api.dto.LegalEntityDto;
import jakarta.transaction.Transactional;

import java.util.List;

public interface LegalEntityService {
    List<LegalEntityDto> findAll();

    LegalEntityDto findById(Integer id);

    @Transactional
    LegalEntityDto create(LegalEntityDto legalEntityDto);

    @Transactional
    LegalEntityDto edit(LegalEntityDto legalEntityDto);

    @Transactional
    void delete(Integer id);
}
