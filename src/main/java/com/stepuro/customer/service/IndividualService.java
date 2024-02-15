package com.stepuro.customer.service;

import com.stepuro.customer.api.dto.IndividualDto;
import com.stepuro.customer.api.dto.mapper.IndividualMapper;
import com.stepuro.customer.api.exceptions.ResourceNotFoundException;
import com.stepuro.customer.model.Individual;
import com.stepuro.customer.repository.IndividualRepositoryJpa;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class IndividualService {
    @Autowired
    private IndividualRepositoryJpa individualRepositoryJpa;

    public List<IndividualDto> findAll(){
        return individualRepositoryJpa
                .findAll()
                .stream()
                .map(IndividualMapper.INSTANCE::individualToIndividualDto)
                .toList();
    }

    public IndividualDto findById(Integer id){
        return IndividualMapper
                .INSTANCE
                .individualToIndividualDto(
                        individualRepositoryJpa
                                .findById(id)
                                .orElseThrow(() -> new ResourceNotFoundException("Individual with id " + id + " not found"))
                );
    }

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

    public void delete(Integer id){
        individualRepositoryJpa.deleteById(id);
    }
}
