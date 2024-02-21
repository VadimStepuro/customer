package com.stepuro.customer.repository.Samples;

import com.stepuro.customer.model.Account;
import com.stepuro.customer.model.enums.AccountStatus;

import java.sql.Timestamp;
import java.time.Instant;

public class AccountSamples {
    public static Account account1 = Account.builder()
            .accountNumber("IE12BOFI90000112345678")
            .status(AccountStatus.ACTIVE)
            .createdDate(Timestamp.from(Instant.now()))
            .updatedDate(Timestamp.from(Instant.now()))
            .build();

    public static Account account2 = Account.builder()
            .accountNumber("IE12BOFI90000112345678")
            .status(AccountStatus.ACTIVE)
            .createdDate(Timestamp.from(Instant.now()))
            .updatedDate(Timestamp.from(Instant.now()))
            .build();

    public static Account account3 = Account.builder()
            .accountNumber("IE12BOFI90000112345678")
            .status(AccountStatus.ACTIVE)
            .createdDate(Timestamp.from(Instant.now()))
            .updatedDate(Timestamp.from(Instant.now()))
            .build();

    public static Account account4 = Account.builder()
            .accountNumber("IE12BOFI90000112345678")
            .status(AccountStatus.ACTIVE)
            .createdDate(Timestamp.from(Instant.now()))
            .updatedDate(Timestamp.from(Instant.now()))
            .build();
}
