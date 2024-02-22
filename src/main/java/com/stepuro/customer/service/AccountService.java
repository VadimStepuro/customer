package com.stepuro.customer.service;

import com.stepuro.customer.api.dto.AccountDto;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface AccountService {
    List<AccountDto> findAll();

    AccountDto findById(UUID id);

    AccountDto findByAccountNumber(String accountNumber);

    boolean existsByAccountNumber(String accountNumber);

    boolean checkLegalEntityOwner(String accountNumber, Integer legalEntityId);

    boolean validateAccountBalance(String accountNumber, BigDecimal amount);

    AccountDto create(AccountDto accountDto);

    AccountDto edit(AccountDto accountDto);

    void delete(UUID id);
}
