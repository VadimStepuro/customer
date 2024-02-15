package com.stepuro.customer.service;

import com.stepuro.customer.api.dto.LegalEntityDto;
import com.stepuro.customer.api.dto.mapper.LegalEntityMapper;
import com.stepuro.customer.api.exceptions.ResourceNotFoundException;
import com.stepuro.customer.model.LegalEntity;
import com.stepuro.customer.repository.LegalEntityRepositoryJpa;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class LegalEntityService {
    @Autowired
    private LegalEntityRepositoryJpa individualRepositoryJpa;

    public List<LegalEntityDto> findAll(){
        return individualRepositoryJpa
                .findAll()
                .stream()
                .map(LegalEntityMapper.INSTANCE::legalEntityToLegalEntityDto)
                .toList();
    }

    public LegalEntityDto findById(Integer id){
        return LegalEntityMapper
                .INSTANCE
                .legalEntityToLegalEntityDto(
                        individualRepositoryJpa
                                .findById(id)
                                .orElseThrow(() -> new ResourceNotFoundException("Individual with id " + id + " not found"))
                );
    }

    public LegalEntityDto create(LegalEntityDto legalEntityDto){
        return LegalEntityMapper
                .INSTANCE
                .legalEntityToLegalEntityDto(
                        individualRepositoryJpa
                                .save(LegalEntityMapper
                                        .INSTANCE
                                        .legalEntityDtoToLegalEntity(legalEntityDto))
                );
    }

    public LegalEntityDto edit(LegalEntityDto legalEntityDto){
        LegalEntity legalEntity = individualRepositoryJpa
                .findById(legalEntityDto.getLegalEntityId())
                .orElseThrow(() -> new ResourceNotFoundException("LegalEntity with id " + legalEntityDto.getLegalEntityId() + " not found"));

        legalEntity.setName(legalEntityDto.getName());
        legalEntity.setInn(legalEntityDto.getInn());
        legalEntity.setAddress(legalEntityDto.getAddress());
        legalEntity.setCity(legalEntityDto.getCity());
        legalEntity.setCreatedDate(legalEntityDto.getCreatedDate());
        legalEntity.setUpdatedDate(legalEntityDto.getUpdatedDate());

        return LegalEntityMapper
                .INSTANCE
                .legalEntityToLegalEntityDto(individualRepositoryJpa.save(legalEntity));
    }

    public void delete(Integer id){
        individualRepositoryJpa.deleteById(id);
    }
}
