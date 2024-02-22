package com.stepuro.customer.service.samples;

import com.stepuro.customer.api.dto.AccountDto;
import com.stepuro.customer.api.dto.mapper.AccountMapper;
import com.stepuro.customer.model.Account;
import com.stepuro.customer.model.enums.AccountStatus;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

public class AccountSamples {
    public static Account account1 = Account.builder()
            .id(UUID.randomUUID())
            .accountNumber("IE12BOFI90000112345678")
            .status(AccountStatus.ACTIVE)
            .createdDate(Timestamp.valueOf(LocalDateTime.now()))
            .updatedDate(Timestamp.valueOf(LocalDateTime.now()))
            .balance(new BigDecimal("200.00"))
            .build();

    public static Account account2 = Account.builder()
            .id(UUID.randomUUID())
            .accountNumber("IE12BOFI90000112345666")
            .status(AccountStatus.ACTIVE)
            .createdDate(Timestamp.from(Instant.now()))
            .updatedDate(Timestamp.from(Instant.now()))
            .balance(new BigDecimal("350.00"))
            .build();

    public static Account account3 = Account.builder()
            .id(UUID.randomUUID())
            .accountNumber("IE12BOFI90000112345655")
            .status(AccountStatus.ACTIVE)
            .createdDate(Timestamp.from(Instant.now()))
            .updatedDate(Timestamp.from(Instant.now()))
            .balance(new BigDecimal("300.00"))
            .build();

    public static Account account4 = Account.builder()
            .id(UUID.randomUUID())
            .accountNumber("IE12BOFI90000112345644")
            .status(AccountStatus.ACTIVE)
            .createdDate(Timestamp.from(Instant.now()))
            .updatedDate(Timestamp.from(Instant.now()))
            .balance(new BigDecimal("400.00"))
            .build();

    public static AccountDto accountDto = AccountMapper.INSTANCE.accountToAccountDto(account1);
}
