package com.stepuro.customer.api.dto.mapper;

import com.stepuro.customer.api.dto.AccountDto;
import com.stepuro.customer.model.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AccountMapper {
    AccountMapper INSTANCE = Mappers.getMapper(AccountMapper.class);
    @Mapping(source = "legalEntityDto", target = "legalEntity")
    Account accountDtoToAccount(AccountDto accountDto);

    @Mapping(source = "legalEntity", target = "legalEntityDto")
    AccountDto accountToAccountDto(Account account);
}
