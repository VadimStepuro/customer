package com.stepuro.customer.service;

import com.stepuro.customer.api.dto.AccountDto;
import com.stepuro.customer.api.exceptions.ResourceNotFoundException;
import com.stepuro.customer.model.Account;
import com.stepuro.customer.model.enums.AccountStatus;
import com.stepuro.customer.repository.AccountRepositoryJpa;
import com.stepuro.customer.service.impl.AccountServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.stepuro.customer.service.samples.AccountSamples.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountServiceImplTests {
    @Mock
    private AccountRepositoryJpa accountRepositoryJpa;

    @InjectMocks
    private AccountServiceImpl accountServiceImpl;

    @Test
    void AccountService_FindAll_ReturnsAllModels(){
        when(accountRepositoryJpa.findAll()).thenReturn(accountList);

        List<AccountDto> allAccounts = accountServiceImpl.findAll();

        assertNotNull(allAccounts);
        assertEquals(4, allAccounts.size());
    }

    @Test
    void AccountService_FindById_ReturnsModel(){
        when(accountRepositoryJpa.findById(any(UUID.class))).thenReturn(Optional.of(account1));

        AccountDto foundAccount = accountServiceImpl.findById(UUID.randomUUID());

        assertNotNull(foundAccount);
        assertEquals(accountDto.getAccountNumber(), foundAccount.getAccountNumber());
        assertEquals(accountDto.getCreatedDate(), foundAccount.getCreatedDate());
        assertEquals(accountDto.getUpdatedDate(), foundAccount.getUpdatedDate());
        assertEquals(accountDto.getStatus(), foundAccount.getStatus());
    }

    @Test
    void AccountService_FindByNumber_ReturnsModel(){
        when(accountRepositoryJpa.findByAccountNumber(any(String.class))).thenReturn(Optional.of(account1));

        AccountDto foundAccount = accountServiceImpl.findByNumber("");

        assertNotNull(foundAccount);
        assertEquals(accountDto.getAccountNumber(), foundAccount.getAccountNumber());
        assertEquals(accountDto.getCreatedDate(), foundAccount.getCreatedDate());
        assertEquals(accountDto.getUpdatedDate(), foundAccount.getUpdatedDate());
        assertEquals(accountDto.getStatus(), foundAccount.getStatus());
    }

    @Test
    void AccountService_Save_ReturnsSavedModel(){
        when(accountRepositoryJpa.save(any(Account.class))).thenReturn(account1);

        AccountDto savedAccount = accountServiceImpl.create(accountDto);

        assertNotNull(account1);
        assertEquals(account1.getAccountNumber(), savedAccount.getAccountNumber());
        assertEquals(account1.getCreatedDate(), savedAccount.getCreatedDate());
        assertEquals(account1.getUpdatedDate(), savedAccount.getUpdatedDate());
        assertEquals(account1.getStatus(), savedAccount.getStatus());
    }

    @Test
    void AccountService_Edit_ReturnsEditedModel(){
        when(accountRepositoryJpa.save(any(Account.class))).thenReturn(account1);
        when(accountRepositoryJpa.findById(any(UUID.class))).thenReturn(Optional.of(account1));

        AccountDto savedAccount = accountServiceImpl.create(accountDto);

        savedAccount.setStatus(AccountStatus.CLOSED);
        savedAccount.setAccountNumber("IE12BOFI90000112345555");
        savedAccount.setCreatedDate(Timestamp.valueOf(LocalDateTime.now().minusMonths(5)));
        savedAccount.setUpdatedDate(Timestamp.valueOf(LocalDateTime.now().minusMonths(3)));
        savedAccount.setBalance(new BigDecimal("550.00"));

        AccountDto editedAccount = accountServiceImpl.edit(savedAccount);

        assertNotNull(editedAccount);
        assertEquals(savedAccount.getAccountNumber(), editedAccount.getAccountNumber());
        assertEquals(savedAccount.getCreatedDate(), editedAccount.getCreatedDate());
        assertEquals(savedAccount.getUpdatedDate(), editedAccount.getUpdatedDate());
        assertEquals(savedAccount.getStatus(), editedAccount.getStatus());
    }



    @Test
    void AccountService_Delete_DeletesModel(){
        when(accountRepositoryJpa.findById(any(UUID.class))).thenReturn(Optional.empty());
        Mockito.doNothing().when(accountRepositoryJpa).deleteById(isA(UUID.class));

        accountServiceImpl.delete(UUID.randomUUID());

        UUID uuid = UUID.randomUUID();

        assertThrows(ResourceNotFoundException.class, () -> accountServiceImpl.findById(uuid));
    }
}
