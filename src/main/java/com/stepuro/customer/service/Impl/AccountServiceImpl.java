package com.stepuro.customer.service.Impl;

import com.stepuro.customer.api.dto.AccountDto;
import com.stepuro.customer.api.dto.TransferEntity;
import com.stepuro.customer.api.dto.mapper.AccountMapper;
import com.stepuro.customer.api.dto.mapper.LegalEntityMapper;
import com.stepuro.customer.api.exceptions.*;
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
    @Transactional
    public void transferAmount(TransferEntity transferEntity) {
        validateTransfer(transferEntity);

        Account sourceAccount = accountRepositoryJpa
                .findByAccountNumber(transferEntity.getSourceNumber())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Account with account number " +
                                transferEntity.getSourceNumber() +
                                " not found"));

        Account destinationAccount = accountRepositoryJpa
                .findByAccountNumber(transferEntity.getDestinationNumber())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Account with account number " +
                                transferEntity.getDestinationNumber() +
                                " not found"));

        sourceAccount.setBalance(sourceAccount.getBalance().subtract(transferEntity.getAmount()));
        destinationAccount.setBalance(destinationAccount.getBalance().add(transferEntity.getAmount()));

        accountRepositoryJpa.save(sourceAccount);
        accountRepositoryJpa.save(destinationAccount);
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

    private boolean existsByAccountNumber(String accountNumber) {
        return accountRepositoryJpa.existsByAccountNumber(accountNumber);
    }

    private boolean validateLegalEntityOwner(String accountNumber, Integer legalEntityId) {
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

    private boolean validateAccountBalance(String accountNumber, BigDecimal amount) {
        Account foundAccount = accountRepositoryJpa
                .findByAccountNumber(accountNumber)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Account with account number " +
                                accountNumber +
                                " not found"));

        int result = foundAccount.getBalance().compareTo(amount);

        return result >= 0;
    }

    private void validateTransfer(TransferEntity transferEntity){
        if(!transferEntity.getSourceNumber().equals(transferEntity.getDestinationNumber()))
            throw new EqualNumberException("Account number can't be equal " +
                    "(source number: " + transferEntity.getSourceNumber() +
                    ", destination number: " + transferEntity.getDestinationNumber());

        if(!validateLegalEntityOwner(transferEntity.getSourceNumber(), transferEntity.getUserId()))
            throw new UserIdDoesntMatchException("Legal entity isn't owner of this account " +
                    "(legalEntity id: " + transferEntity.getUserId() +
                    ", account number: " + transferEntity.getSourceNumber());

        if(!validateAccountBalance(transferEntity.getSourceNumber(), transferEntity.getAmount()))
            throw new NotEnoughMoneyException("Not enough money on account with number " + transferEntity.getSourceNumber() +
                    " to transfer " + transferEntity.getAmount());
    }
}
