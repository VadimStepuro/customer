package com.stepuro.customer.api.dto.mapper;

import com.stepuro.customer.api.dto.LegalEntityDto;
import com.stepuro.customer.model.LegalEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface LegalEntityMapper {
        LegalEntityMapper INSTANCE = Mappers.getMapper(LegalEntityMapper.class);

        LegalEntity legalEntityDtoToLegalEntity(LegalEntityDto legalEntityDto);

        LegalEntityDto legalEntityToLegalEntityDto(LegalEntity legalEntity);
}
