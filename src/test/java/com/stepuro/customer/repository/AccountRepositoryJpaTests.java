package com.stepuro.customer.repository;

import com.stepuro.customer.model.Account;
import com.stepuro.customer.model.enums.AccountStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import static com.stepuro.customer.repository.Samples.AccountSamples.*;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class AccountRepositoryJpaTests {
    @Autowired
    private AccountRepositoryJpa accountRepositoryJpa;

    @Test
    public void AccountRepository_Save_ReturnsSavedModel(){
        Account savedAccount = accountRepositoryJpa.save(account1);

        assertNotNull(savedAccount);
        assertEquals(account1.getAccountNumber(), savedAccount.getAccountNumber());
        assertEquals(account1.getCreatedDate(), savedAccount.getCreatedDate());
        assertEquals(account1.getUpdatedDate(), savedAccount.getUpdatedDate());
        assertEquals(account1.getStatus(), savedAccount.getStatus());
        assertEquals(account1.getBalance(), savedAccount.getBalance());
    }

    @Test
    public void AccountRepository_FindAll_ReturnsAllModels(){
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
        Account savedAccount = accountRepositoryJpa.save(account1);
        accountRepositoryJpa.save(account2);

        Account account = accountRepositoryJpa.findById(savedAccount.getId()).get();

        assertNotNull(account);
        assertEquals(savedAccount.getId(), account.getId());
        assertEquals(savedAccount.getAccountNumber(), account.getAccountNumber());
        assertEquals(savedAccount.getCreatedDate(), account.getCreatedDate());
        assertEquals(savedAccount.getUpdatedDate(), account.getUpdatedDate());
        assertEquals(savedAccount.getStatus(), account.getStatus());
        assertEquals(savedAccount.getBalance(), account.getBalance());
    }

    @Test
    public void AccountRepository_FindByNumber_ReturnsModel(){
        Account savedAccount = accountRepositoryJpa.save(account1);
        accountRepositoryJpa.save(account2);

        Account account = accountRepositoryJpa.findByAccountNumber(savedAccount.getAccountNumber()).get();

        assertNotNull(account);
        assertEquals(savedAccount.getId(), account.getId());
        assertEquals(savedAccount.getAccountNumber(), account.getAccountNumber());
        assertEquals(savedAccount.getCreatedDate(), account.getCreatedDate());
        assertEquals(savedAccount.getUpdatedDate(), account.getUpdatedDate());
        assertEquals(savedAccount.getStatus(), account.getStatus());
        assertEquals(savedAccount.getBalance(), account.getBalance());
    }

    @Test
    public void AccountRepository_ExistsByNumber_ReturnsTrue(){
        Account savedAccount = accountRepositoryJpa.save(account1);

        Boolean result = accountRepositoryJpa.existsByAccountNumber(savedAccount.getAccountNumber());

        assertTrue(result);
    }

    @Test
    public void AccountRepository_Update_ChangesModel(){
        Account savedAccount = accountRepositoryJpa.save(account1);

        savedAccount.setStatus(AccountStatus.CLOSED);
        savedAccount.setAccountNumber("IE12BOFI90000112345555");
        savedAccount.setCreatedDate(Timestamp.valueOf(LocalDateTime.now().minusMonths(5)));
        savedAccount.setUpdatedDate(Timestamp.valueOf(LocalDateTime.now().minusMonths(3)));
        savedAccount.setBalance(new BigDecimal("100.00"));


        Account updatedAccount = accountRepositoryJpa.save(savedAccount);

        assertNotNull(updatedAccount);
        assertEquals(savedAccount.getId(), updatedAccount.getId());
        assertEquals(savedAccount.getStatus(), updatedAccount.getStatus());
        assertEquals(savedAccount.getAccountNumber(), updatedAccount.getAccountNumber());
        assertEquals(savedAccount.getCreatedDate(), updatedAccount.getCreatedDate());
        assertEquals(savedAccount.getUpdatedDate(), updatedAccount.getUpdatedDate());
        assertEquals(savedAccount.getBalance(), updatedAccount.getBalance());
    }

    @Test
    public void AccountRepository_Remove_RemovesModel(){
        Account savedAircompany = accountRepositoryJpa.save(account1);

        accountRepositoryJpa.deleteById(savedAircompany.getId());

        boolean existsById = accountRepositoryJpa.existsById(savedAircompany.getId());

        assertFalse(existsById);
    }
}
