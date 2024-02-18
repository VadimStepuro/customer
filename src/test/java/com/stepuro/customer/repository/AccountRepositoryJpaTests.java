package com.stepuro.customer.repository;

import com.stepuro.customer.model.Account;
import com.stepuro.customer.model.status.AccountStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class AccountRepositoryJpaTests {
    @Autowired
    private AccountRepositoryJpa accountRepositoryJpa;

    @Test
    public void AccountRepository_Save_ReturnsSavedModel(){
        //Arrange
        Account account = Account.builder()
                .accountNumber("IE12BOFI90000112345678")
                .status(AccountStatus.ACTIVE)
                .createdDate(Date.valueOf(LocalDate.now()))
                .updatedDate(Date.valueOf(LocalDate.now()))
                .build();

        //Act
        Account savedAccount = accountRepositoryJpa.save(account);

        //Assert
        assertNotNull(savedAccount);
        assertEquals(account.getAccountNumber(), savedAccount.getAccountNumber());
        assertEquals(account.getCreatedDate(), savedAccount.getCreatedDate());
        assertEquals(account.getUpdatedDate(), savedAccount.getUpdatedDate());
        assertEquals(account.getStatus(), savedAccount.getStatus());
    }

    @Test
    public void AccountRepository_FindAll_ReturnsAllModels(){
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

        accountRepositoryJpa.save(account1);
        accountRepositoryJpa.save(account2);
        accountRepositoryJpa.save(account3);
        accountRepositoryJpa.save(account4);

        List<Account> accounts = accountRepositoryJpa.findAll();

        assertNotNull(accounts);
        assertEquals(4, accounts.size());
    }

    @Test
    public void AccountRepository_FindById_ReturnsModel(){
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

        Account savedAccount = accountRepositoryJpa.save(account1);
        accountRepositoryJpa.save(account2);


        Account account = accountRepositoryJpa.findById(savedAccount.getId()).get();

        assertNotNull(account);
        assertEquals(account1.getId(), account.getId());
        assertEquals(account.getAccountNumber(), savedAccount.getAccountNumber());
        assertEquals(account.getCreatedDate(), savedAccount.getCreatedDate());
        assertEquals(account.getUpdatedDate(), savedAccount.getUpdatedDate());
        assertEquals(account.getStatus(), savedAccount.getStatus());
    }

    @Test
    public void AccountRepository_Update_ChangesModel(){
        Account account1 = Account.builder()
                .accountNumber("IE12BOFI90000112345678")
                .status(AccountStatus.ACTIVE)
                .createdDate(Date.valueOf(LocalDate.now()))
                .updatedDate(Date.valueOf(LocalDate.now()))
                .build();


        Account savedAccount = accountRepositoryJpa.save(account1);

        savedAccount.setStatus(AccountStatus.CLOSED);
        savedAccount.setAccountNumber("IE12BOFI90000112345555");
        savedAccount.setCreatedDate(Date.valueOf(LocalDate.now().minusMonths(5)));
        savedAccount.setUpdatedDate(Date.valueOf(LocalDate.now().minusMonths(3)));

        Account updatedAccount = accountRepositoryJpa.save(savedAccount);

        assertNotNull(updatedAccount);
        assertEquals(savedAccount.getId(), updatedAccount.getId());
        assertEquals(savedAccount.getStatus(), updatedAccount.getStatus());
        assertEquals(savedAccount.getAccountNumber(), updatedAccount.getAccountNumber());
        assertEquals(savedAccount.getCreatedDate(), updatedAccount.getCreatedDate());
        assertEquals(savedAccount.getUpdatedDate(), updatedAccount.getUpdatedDate());
    }

    @Test
    public void AccountRepository_Remove_RemovesModel(){
        Account account1 = Account.builder()
                .accountNumber("IE12BOFI90000112345678")
                .status(AccountStatus.ACTIVE)
                .createdDate(Date.valueOf(LocalDate.now()))
                .updatedDate(Date.valueOf(LocalDate.now()))
                .build();

        Account savedAircompany = accountRepositoryJpa.save(account1);

        accountRepositoryJpa.deleteById(savedAircompany.getId());

        boolean existsById = accountRepositoryJpa.existsById(savedAircompany.getId());

        assertFalse(existsById);
    }
}
