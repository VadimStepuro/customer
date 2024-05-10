package com.stepuro.customer.service;

import com.stepuro.customer.api.dto.AccountDto;
import com.stepuro.customer.api.dto.TransferEntity;

import java.util.List;
import java.util.UUID;

public interface AccountService {
    List<AccountDto> findAll();

    AccountDto findById(UUID id);

    AccountDto findByNumber(String accountNumber);

    boolean existsByAccountNumber(String accountNumber);

    void transferAmount(TransferEntity transferEntity);

    AccountDto create(AccountDto accountDto);

    AccountDto edit(AccountDto accountDto);

    void delete(UUID id);
}
