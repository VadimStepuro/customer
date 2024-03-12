package com.stepuro.customer.repository;

import com.stepuro.customer.api.exceptions.ResourceNotFoundException;
import com.stepuro.customer.model.Account;
import com.stepuro.customer.model.enums.AccountStatus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

import static com.stepuro.customer.repository.Samples.AccountSamples.account1;
import static org.junit.jupiter.api.Assertions.*;

@JdbcTest
public class AccountRepositoryJdbcTests {
    private AccountRepositoryJdbc accountRepositoryJdbc;
    private EmbeddedDatabase embeddedDatabase;

    @BeforeEach
    public void setup(){
        embeddedDatabase = new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2)
                .addScript("/sql/01.create-legal-entity.sql")
                .build();

        accountRepositoryJdbc = new AccountRepositoryJdbc();
        accountRepositoryJdbc.setDataSource(embeddedDatabase);
    }

    @AfterEach
    public void tearDown(){
        embeddedDatabase.shutdown();
    }

    @Test
    public void AccountRepositoryJdbc_Save_SavesModel(){
        UUID id = accountRepositoryJdbc.save(account1);

        Account savedAccount = accountRepositoryJdbc.findById(id);

        assertNotNull(id);
        assertEquals(account1.getAccountNumber(), savedAccount.getAccountNumber());
        assertEquals(account1.getCreatedDate().getTime(), savedAccount.getCreatedDate().getTime());
        assertEquals(account1.getUpdatedDate().getTime(), savedAccount.getUpdatedDate().getTime());
        assertEquals(account1.getBalance(), savedAccount.getBalance());
        assertEquals(account1.getStatus(), savedAccount.getStatus());
    }

    @Test
    public void AccountRepositoryJdbc_Edit_ChangesModel(){
        UUID uuid = accountRepositoryJdbc.save(account1);

        Account savedAccount = accountRepositoryJdbc.findById(uuid);

        savedAccount.setId(uuid);
        savedAccount.setStatus(AccountStatus.CLOSED);
        savedAccount.setAccountNumber("IE12BOFI90000112345555");
        savedAccount.setCreatedDate(Timestamp.valueOf(LocalDateTime.now().minusMonths(5)));
        savedAccount.setUpdatedDate(Timestamp.valueOf(LocalDateTime.now().minusMonths(3)));
        savedAccount.setBalance(new BigDecimal("500.00"));

        accountRepositoryJdbc.edit(savedAccount);

        Account account = accountRepositoryJdbc.findById(uuid);

        assertNotNull(account);
        assertEquals(savedAccount.getAccountNumber(), account.getAccountNumber());
        assertEquals(savedAccount.getStatus(), account.getStatus());
        assertEquals(savedAccount.getCreatedDate().getTime(), account.getCreatedDate().getTime());
        assertEquals(savedAccount.getUpdatedDate().getTime(), account.getUpdatedDate().getTime());
        assertEquals(savedAccount.getBalance(), account.getBalance());
    }

    @Test
    public void AccountRepositoryJdbc_FindById_ReturnsModel(){
        UUID uuid = accountRepositoryJdbc.save(account1);

        Account account = accountRepositoryJdbc.findById(uuid);

        assertNotNull(account);
        assertEquals(account1.getAccountNumber(), account.getAccountNumber());
        assertEquals(account1.getStatus(), account.getStatus());
        assertEquals(account1.getCreatedDate().getTime(), account.getCreatedDate().getTime());
        assertEquals(account1.getUpdatedDate().getTime(), account.getUpdatedDate().getTime());
        assertEquals(account1.getBalance(), account.getBalance());
    }

    @Test
    public void AccountRepositoryJdbc_Remove_RemovesModel(){
        UUID uuid = accountRepositoryJdbc.save(account1);

        accountRepositoryJdbc.deleteById(uuid);

        assertThrows(ResourceNotFoundException.class, () -> accountRepositoryJdbc.findById(uuid));

    }
}
