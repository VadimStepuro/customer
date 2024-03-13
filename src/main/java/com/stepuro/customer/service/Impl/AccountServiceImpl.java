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
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static com.stepuro.customer.utils.AccountUtils.*;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountRepositoryJpa accountRepositoryJpa;

    @Override
    @Cacheable(cacheNames = "accounts", keyGenerator = "newKeyGenerator")
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
    @Cacheable(cacheNames = "accountById", key = "#id")
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
    @Cacheable(cacheNames = "accountByNumber", key = "#accountNumber")
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
    @Transactional
    @Caching(
            evict = {
                    @CacheEvict(cacheNames = "accounts", allEntries = true),
                    @CacheEvict(cacheNames = "accountById", allEntries = true),
                    @CacheEvict(cacheNames = "accountByNumber", key = "#transferEntity.sourceNumber"),
                    @CacheEvict(cacheNames = "accountByNumber", key = "#transferEntity.destinationNumber")
            }
    )
    public void transferAmount(TransferEntity transferEntity) {
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

        validateTransfer(transferEntity, sourceAccount, destinationAccount);

        sourceAccount.setBalance(sourceAccount.getBalance().subtract(transferEntity.getAmount()));
        destinationAccount.setBalance(destinationAccount.getBalance().add(transferEntity.getAmount()));

        accountRepositoryJpa.save(sourceAccount);
        accountRepositoryJpa.save(destinationAccount);
    }

    @Override
    @Transactional
    @Caching(
            put = {
                    @CachePut(cacheNames = "accountByNumber", key = "#accountDto.accountNumber")
            },
            evict = {
                    @CacheEvict(cacheNames = "accounts", allEntries = true)
            }
    )
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
    @Caching(
            put = {
                    @CachePut(cacheNames = "accountById", key = "#accountDto.id"),
                    @CachePut(cacheNames = "accountByNumber", key = "#accountDto.accountNumber")
            },
            evict = {
                    @CacheEvict(cacheNames = "accounts", allEntries = true)
            }
    )
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
    @Caching(
            evict = {
                    @CacheEvict(cacheNames = "accounts", allEntries = true),
                    @CacheEvict(cacheNames = "accountByNumber", allEntries = true),
                    @CacheEvict(cacheNames = "accountById", key = "#id")
            }
    )
    public void delete(UUID id){
        accountRepositoryJpa.deleteById(id);
    }

    private void validateTransfer(TransferEntity transferEntity, Account sourceAccount, Account destinationAccount){
        if(transferEntity.getSourceNumber().equals(transferEntity.getDestinationNumber()))
            throw new EqualNumberException("Account number can't be equal " +
                    "(source number: " + transferEntity.getSourceNumber() +
                    ", destination number: " + transferEntity.getDestinationNumber() + ")");

        if(!validateLegalEntityOwner(sourceAccount, transferEntity.getUserId()))
            throw new UserIdDoesntMatchException("Legal entity isn't owner of this account " +
                    "(legalEntity id: " + transferEntity.getUserId() +
                    ", account number: " + transferEntity.getSourceNumber());

        if(!validateStatus(sourceAccount))
            throw new StatusException("Source account is closed");

        if(!validateStatus(destinationAccount))
            throw new StatusException("Destination account is closed");

        if(!validateAccountBalance(sourceAccount, transferEntity.getAmount()))
            throw new NotEnoughMoneyException("Not enough money on account with number " + transferEntity.getSourceNumber() +
                    " to transfer " + transferEntity.getAmount());
    }
}
