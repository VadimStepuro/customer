package com.stepuro.customer.api.dto.mapper;

import com.stepuro.customer.api.dto.LegalEntityDto;
import com.stepuro.customer.model.LegalEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface LegalEntityMapper {
        LegalEntityMapper INSTANCE = Mappers.getMapper(LegalEntityMapper.class);

        @Mapping(target = "accounts", ignore = true)
        LegalEntity legalEntityDtoToLegalEntity(LegalEntityDto legalEntityDto);

        LegalEntityDto legalEntityToLegalEntityDto(LegalEntity legalEntity);
}
