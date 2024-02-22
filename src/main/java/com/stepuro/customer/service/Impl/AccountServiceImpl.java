package com.stepuro.customer.service.Impl;

import com.stepuro.customer.api.dto.AccountDto;
import com.stepuro.customer.api.dto.mapper.AccountMapper;
import com.stepuro.customer.api.dto.mapper.LegalEntityMapper;
import com.stepuro.customer.api.exceptions.NoContentException;
import com.stepuro.customer.api.exceptions.ResourceNotFoundException;
import com.stepuro.customer.model.Account;
import com.stepuro.customer.repository.AccountRepositoryJpa;
import com.stepuro.customer.service.AccountService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountRepositoryJpa accountRepositoryJpa;

    @Override
    public List<AccountDto> findAll(){
        List<AccountDto> accountDtos = accountRepositoryJpa
                .findAll()
                .stream()
                .map(AccountMapper.INSTANCE::accountToAccountDto)
                .toList();

        if(accountDtos.isEmpty())
            throw new NoContentException("No accounts found");

        return accountDtos;
    }

    @Override
    public AccountDto findById(UUID id){
        return AccountMapper
                .INSTANCE
                .accountToAccountDto(
                        accountRepositoryJpa
                                .findById(id)
                                .orElseThrow(() -> new ResourceNotFoundException("Account with id " + id + " not found"))
                );
    }

    @Override
    public AccountDto findByAccountNumber(String accountNumber) {
        return AccountMapper
                .INSTANCE
                .accountToAccountDto(
                        accountRepositoryJpa
                                .findByAccountNumber(accountNumber)
                                .orElseThrow(() -> new ResourceNotFoundException(
                                        "Account with account number " +
                                                accountNumber +
                                                " not found")));
    }

    @Override
    public boolean existsByAccountNumber(String accountNumber) {
        return accountRepositoryJpa.existsByAccountNumber(accountNumber);
    }

    @Override
    public boolean checkLegalEntityOwner(String accountNumber, Integer legalEntityId) {
        Account foundAccount = accountRepositoryJpa
                .findByAccountNumber(accountNumber)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Account with account number " +
                                accountNumber +
                                " not found"));

        if(foundAccount.getLegalEntity() == null)
            return false;

        return foundAccount.getLegalEntity().getLegalEntityId().equals(legalEntityId);
    }

    @Override
    public boolean validateAccountBalance(String accountNumber, BigDecimal amount) {
        Account foundAccount = accountRepositoryJpa
                .findByAccountNumber(accountNumber)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Account with account number " +
                                accountNumber +
                                " not found"));

        int result = foundAccount.getBalance().compareTo(amount);

        return result >= 0;
    }

    @Override
    @Transactional
    public AccountDto create(AccountDto accountDto){
        return AccountMapper
                .INSTANCE
                .accountToAccountDto(
                        accountRepositoryJpa
                                .save(AccountMapper
                                        .INSTANCE
                                        .accountDtoToAccount(accountDto))
                );
    }

    @Override
    @Transactional
    public AccountDto edit(AccountDto accountDto){
        Account account = accountRepositoryJpa
                .findById(accountDto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Account with id " + accountDto.getId() + " not found"));

        account.setAccountNumber(accountDto.getAccountNumber());
        account.setCreatedDate(accountDto.getCreatedDate());
        account.setUpdatedDate(accountDto.getUpdatedDate());
        account.setStatus(accountDto.getStatus());
        account.setBalance(accountDto.getBalance());
        account.setLegalEntity(
                LegalEntityMapper
                    .INSTANCE
                    .legalEntityDtoToLegalEntity(accountDto.getLegalEntityDto()));

        return AccountMapper
                .INSTANCE
                .accountToAccountDto(accountRepositoryJpa.save(account));
    }

    @Override
    @Transactional
    public void delete(UUID id){
        accountRepositoryJpa.deleteById(id);
    }


}
