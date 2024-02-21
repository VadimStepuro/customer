package com.stepuro.customer.service;

import com.stepuro.customer.api.dto.AccountDto;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.UUID;

public interface AccountService {
    List<AccountDto> findAll();

    AccountDto findById(UUID id);

    @Transactional
    AccountDto create(AccountDto accountDto);

    @Transactional
    AccountDto edit(AccountDto accountDto);

    @Transactional
    void delete(UUID id);
}
