package com.stepuro.customer.service.Impl;

import com.stepuro.customer.api.dto.LegalEntityDto;
import com.stepuro.customer.api.dto.mapper.LegalEntityMapper;
import com.stepuro.customer.api.exceptions.NoContentException;
import com.stepuro.customer.api.exceptions.ResourceNotFoundException;
import com.stepuro.customer.model.LegalEntity;
import com.stepuro.customer.repository.LegalEntityRepositoryJpa;
import com.stepuro.customer.service.LegalEntityService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LegalEntityServiceImpl implements LegalEntityService {
    @Autowired
    private LegalEntityRepositoryJpa individualRepositoryJpa;

    @Override
    public List<LegalEntityDto> findAll(){
        List<LegalEntityDto> legalEntityDtos = individualRepositoryJpa
                .findAll()
                .stream()
                .map(LegalEntityMapper.INSTANCE::legalEntityToLegalEntityDto)
                .toList();

        if(legalEntityDtos.isEmpty())
            throw new NoContentException("No legal entity found");

        return legalEntityDtos;
    }

    @Override
    public LegalEntityDto findById(Integer id){
        return LegalEntityMapper
                .INSTANCE
                .legalEntityToLegalEntityDto(
                        individualRepositoryJpa
                                .findById(id)
                                .orElseThrow(() -> new ResourceNotFoundException("Individual with id " + id + " not found"))
                );
    }

    @Override
    @Transactional
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

    @Override
    @Transactional
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

    @Override
    @Transactional
    public void delete(Integer id){
        individualRepositoryJpa.deleteById(id);
    }
}
