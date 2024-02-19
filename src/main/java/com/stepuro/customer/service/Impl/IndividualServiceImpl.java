package com.stepuro.customer.service.Impl;

import com.stepuro.customer.api.dto.IndividualDto;
import com.stepuro.customer.api.dto.mapper.IndividualMapper;
import com.stepuro.customer.api.exceptions.NoContentException;
import com.stepuro.customer.api.exceptions.ResourceNotFoundException;
import com.stepuro.customer.model.Individual;
import com.stepuro.customer.repository.IndividualRepositoryJpa;
import com.stepuro.customer.service.IndividualService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class IndividualServiceImpl implements IndividualService {
    @Autowired
    private IndividualRepositoryJpa individualRepositoryJpa;

    @Override
    public List<IndividualDto> findAll(){
        List<IndividualDto> individualDtos = individualRepositoryJpa
                .findAll()
                .stream()
                .map(IndividualMapper.INSTANCE::individualToIndividualDto)
                .toList();

        if(individualDtos.isEmpty())
            throw new NoContentException("No individuals found");

        return individualDtos;
    }

    @Override
    public IndividualDto findById(Integer id){
        return IndividualMapper
                .INSTANCE
                .individualToIndividualDto(
                        individualRepositoryJpa
                                .findById(id)
                                .orElseThrow(() -> new ResourceNotFoundException("Individual with id " + id + " not found"))
                );
    }

    @Override
    public IndividualDto create(IndividualDto individualDto){
        return IndividualMapper
                .INSTANCE
                .individualToIndividualDto(
                        individualRepositoryJpa
                                .save(IndividualMapper
                                        .INSTANCE
                                        .individualDtoToIndividual(individualDto))
                );
    }

    @Override
    public IndividualDto edit(IndividualDto individualDto){
        Individual individual = individualRepositoryJpa
                .findById(individualDto.getIndividualId())
                .orElseThrow(() -> new ResourceNotFoundException("Individual with id " + individualDto.getIndividualId() + " not found"));

        individual.setName(individualDto.getName());
        individual.setLastName(individualDto.getLastName());
        individual.setDayOfBirth(individualDto.getDayOfBirth());
        individual.setAddress(individualDto.getAddress());
        individual.setCity(individualDto.getCity());
        individual.setCreatedDate(individualDto.getCreatedDate());
        individual.setUpdatedDate(individualDto.getUpdatedDate());

        return IndividualMapper
                .INSTANCE
                .individualToIndividualDto(individualRepositoryJpa.save(individual));
    }

    @Override
    public void delete(Integer id){
        individualRepositoryJpa.deleteById(id);
    }
}
