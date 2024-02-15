package com.stepuro.customer.api.dto.mapper;

import com.stepuro.customer.api.dto.IndividualDto;
import com.stepuro.customer.model.Individual;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface IndividualMapper {
    IndividualMapper INSTANCE = Mappers.getMapper(IndividualMapper.class);

    Individual individualDtoToIndividual(IndividualDto individualDto);

    IndividualDto individualToIndividualDto(Individual individual);
}
