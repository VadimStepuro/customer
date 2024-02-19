package com.stepuro.customer.service;

import com.stepuro.customer.api.dto.AccountDto;
import com.stepuro.customer.api.exceptions.ResourceNotFoundException;
import com.stepuro.customer.model.Account;
import com.stepuro.customer.model.status.AccountStatus;
import com.stepuro.customer.repository.AccountRepositoryJpa;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTests {
    @Mock
    private AccountRepositoryJpa accountRepositoryJpa;

    @InjectMocks
    private AccountService accountService;

    @Test
    public void AccountService_FindAll_ReturnsAllModels(){
        Account account1 = Account.builder()
                .accountNumber("IE12BOFI90000112345678")
                .status(AccountStatus.ACTIVE)
                .createdDate(Date.valueOf(LocalDate.now()))
                .updatedDate(Date.valueOf(LocalDate.now()))
                .build();
        Account account2 = Account.builder()
                .accountNumber("IE12BOFI90000112345555")
                .status(AccountStatus.ACTIVE)
                .createdDate(Date.valueOf(LocalDate.now()))
                .updatedDate(Date.valueOf(LocalDate.now()))
                .build();
        Account account3 = Account.builder()
                .accountNumber("IE12BOFI90000112345444")
                .status(AccountStatus.CLOSED)
                .createdDate(Date.valueOf(LocalDate.now().minusMonths(5)))
                .updatedDate(Date.valueOf(LocalDate.now().minusMonths(5)))
                .build();
        Account account4 = Account.builder()
                .accountNumber("IE12BOFI90000112345333")
                .status(AccountStatus.ACTIVE)
                .createdDate(Date.valueOf(LocalDate.now().minusMonths(3)))
                .updatedDate(Date.valueOf(LocalDate.now().minusMonths(2)))
                .build();

        List<Account> accounts = List.of(account1, account2, account3, account4);

        when(accountRepositoryJpa.findAll()).thenReturn(accounts);

        List<AccountDto> allAccounts = accountService.findAll();

        assertNotNull(allAccounts);
        assertEquals(4, allAccounts.size());
    }

    @Test
    public void AccountService_FindById_ReturnsModel(){
        AccountDto accountDto = AccountDto.builder()
                .accountNumber("IE12BOFI90000112345678")
                .status(AccountStatus.ACTIVE)
                .createdDate(Date.valueOf(LocalDate.now()))
                .updatedDate(Date.valueOf(LocalDate.now()))
                .build();

        Account account = Account.builder()
                .accountNumber("IE12BOFI90000112345678")
                .status(AccountStatus.ACTIVE)
                .createdDate(accountDto.getCreatedDate())
                .updatedDate(accountDto.getUpdatedDate())
                .build();

        when(accountRepositoryJpa.findById(any(UUID.class))).thenReturn(Optional.of(account));

        AccountDto foundAccount = accountService.findById(UUID.randomUUID());

        assertNotNull(account);
        assertEquals(accountDto.getAccountNumber(), foundAccount.getAccountNumber());
        assertEquals(accountDto.getCreatedDate(), foundAccount.getCreatedDate());
        assertEquals(accountDto.getUpdatedDate(), foundAccount.getUpdatedDate());
        assertEquals(accountDto.getStatus(), foundAccount.getStatus());
    }

    @Test
    public void AccountService_Save_ReturnsSavedModel(){

        AccountDto accountDto = AccountDto.builder()
                .accountNumber("IE12BOFI90000112345678")
                .status(AccountStatus.ACTIVE)
                .createdDate(Date.valueOf(LocalDate.now()))
                .updatedDate(Date.valueOf(LocalDate.now()))
                .build();

        Account account = Account.builder()
                .accountNumber("IE12BOFI90000112345678")
                .status(AccountStatus.ACTIVE)
                .createdDate(Date.valueOf(LocalDate.now()))
                .updatedDate(Date.valueOf(LocalDate.now()))
                .build();

        when(accountRepositoryJpa.save(any(Account.class))).thenReturn(account);

        AccountDto savedAccount = accountService.create(accountDto);

        assertNotNull(account);
        assertEquals(account.getAccountNumber(), savedAccount.getAccountNumber());
        assertEquals(account.getCreatedDate(), savedAccount.getCreatedDate());
        assertEquals(account.getUpdatedDate(), savedAccount.getUpdatedDate());
        assertEquals(account.getStatus(), savedAccount.getStatus());
    }

    @Test
    public void AccountService_Edit_ReturnsEditedModel(){

        AccountDto accountDto = AccountDto.builder()
                .id(UUID.randomUUID())
                .accountNumber("IE12BOFI90000112345678")
                .status(AccountStatus.ACTIVE)
                .createdDate(Date.valueOf(LocalDate.now()))
                .updatedDate(Date.valueOf(LocalDate.now()))
                .build();

        Account account = Account.builder()
                .id(accountDto.getId())
                .accountNumber("IE12BOFI90000112345678")
                .status(AccountStatus.ACTIVE)
                .createdDate(Date.valueOf(LocalDate.now()))
                .updatedDate(Date.valueOf(LocalDate.now()))
                .build();

        when(accountRepositoryJpa.save(any(Account.class))).thenReturn(account);
        when(accountRepositoryJpa.findById(any(UUID.class))).thenReturn(Optional.of(account));

        AccountDto savedAccount = accountService.create(accountDto);

        savedAccount.setStatus(AccountStatus.CLOSED);
        savedAccount.setAccountNumber("IE12BOFI90000112345555");
        savedAccount.setCreatedDate(Date.valueOf(LocalDate.now().minusMonths(5)));
        savedAccount.setUpdatedDate(Date.valueOf(LocalDate.now().minusMonths(3)));


        AccountDto editedAccount = accountService.edit(savedAccount);

        assertNotNull(editedAccount);
        assertEquals(savedAccount.getAccountNumber(), editedAccount.getAccountNumber());
        assertEquals(savedAccount.getCreatedDate(), editedAccount.getCreatedDate());
        assertEquals(savedAccount.getUpdatedDate(), editedAccount.getUpdatedDate());
        assertEquals(savedAccount.getStatus(), editedAccount.getStatus());
    }



    @Test
    public void AccountService_Delete_DeletesModel(){
        when(accountRepositoryJpa.findById(any(UUID.class))).thenReturn(Optional.empty());
        Mockito.doNothing().when(accountRepositoryJpa).deleteById(isA(UUID.class));

        accountService.delete(UUID.randomUUID());

        assertThrows(ResourceNotFoundException.class, () -> accountService.findById(UUID.randomUUID()));
    }
}
