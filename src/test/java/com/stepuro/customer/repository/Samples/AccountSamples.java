package com.stepuro.customer.repository.Samples;

import com.stepuro.customer.model.Account;
import com.stepuro.customer.model.enums.AccountStatus;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

public class AccountSamples {
    public static Account account1 = Account.builder()
            .accountNumber("IE12BOFI90000112345678")
            .status(AccountStatus.ACTIVE)
            .createdDate(Timestamp.from(Instant.now()))
            .updatedDate(Timestamp.from(Instant.now()))
            .balance(new BigDecimal("300.00"))
            .build();

    public static Account account2 = Account.builder()
            .accountNumber("IE12BOFI90000112345666")
            .status(AccountStatus.ACTIVE)
            .createdDate(Timestamp.from(Instant.now()))
            .updatedDate(Timestamp.from(Instant.now()))
            .balance(new BigDecimal("400.00"))
            .build();

    public static Account account3 = Account.builder()
            .accountNumber("IE12BOFI90000112345655")
            .status(AccountStatus.ACTIVE)
            .createdDate(Timestamp.from(Instant.now()))
            .updatedDate(Timestamp.from(Instant.now()))
            .balance(new BigDecimal("350.00"))
            .build();

    public static Account account4 = Account.builder()
            .accountNumber("IE12BOFI90000112345644")
            .status(AccountStatus.ACTIVE)
            .createdDate(Timestamp.from(Instant.now()))
            .updatedDate(Timestamp.from(Instant.now()))
            .balance(new BigDecimal("200.00"))
            .build();

    public static List<Account> accountList = List.of(account1, account2, account3, account4);
}
