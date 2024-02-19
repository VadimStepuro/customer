package com.stepuro.customer.service.samples;

import com.stepuro.customer.api.dto.AccountDto;
import com.stepuro.customer.api.dto.mapper.AccountMapper;
import com.stepuro.customer.model.Account;
import com.stepuro.customer.model.enums.AccountStatus;

import java.sql.Date;
import java.time.LocalDate;
import java.util.UUID;

public class AccountSamples {
    public static Account account1 = Account.builder()
            .id(UUID.randomUUID())
            .accountNumber("IE12BOFI90000112345678")
            .status(AccountStatus.ACTIVE)
            .createdDate(Date.valueOf(LocalDate.now()))
            .updatedDate(Date.valueOf(LocalDate.now()))
            .build();

    public static Account account2 = Account.builder()
            .id(UUID.randomUUID())
            .accountNumber("IE12BOFI90000112345678")
            .status(AccountStatus.ACTIVE)
            .createdDate(Date.valueOf(LocalDate.now()))
            .updatedDate(Date.valueOf(LocalDate.now()))
            .build();

    public static Account account3 = Account.builder()
            .id(UUID.randomUUID())
            .accountNumber("IE12BOFI90000112345678")
            .status(AccountStatus.ACTIVE)
            .createdDate(Date.valueOf(LocalDate.now()))
            .updatedDate(Date.valueOf(LocalDate.now()))
            .build();

    public static Account account4 = Account.builder()
            .id(UUID.randomUUID())
            .accountNumber("IE12BOFI90000112345678")
            .status(AccountStatus.ACTIVE)
            .createdDate(Date.valueOf(LocalDate.now()))
            .updatedDate(Date.valueOf(LocalDate.now()))
            .build();

    public static AccountDto accountDto = AccountMapper.INSTANCE.accountToAccountDto(account1);
}
