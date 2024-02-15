package com.stepuro.customer.api.dto.mapper;

import com.stepuro.customer.api.dto.AccountDto;
import com.stepuro.customer.model.Account;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AccountMapper {
    AccountMapper INSTANCE = Mappers.getMapper(AccountMapper.class);

    Account accountDtoToAccount(AccountDto accountDto);

    AccountDto accountToAccountDto(Account account);
}
