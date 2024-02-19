package com.stepuro.customer.repository;

import com.stepuro.customer.model.Account;
import com.stepuro.customer.model.enums.AccountStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;
import java.time.LocalDate;
import java.util.UUID;

import static com.stepuro.customer.repository.Samples.AccountSamples.account1;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class AccountRepositoryJDBCTests {
    @Autowired
    private AccountRepositoryJDBC accountRepositoryJDBC;

    @Test
    public void AccountRepositoryJDBC_Save_SavesModel(){
        UUID result = accountRepositoryJDBC.save(account1);

        assertNotNull(result);
    }

    @Test
    public void AccountRepositoryJDBC_Edit_ChangesModel(){
        UUID uuid = accountRepositoryJDBC.save(account1);

        account1.setStatus(AccountStatus.CLOSED);
        account1.setAccountNumber("IE12BOFI90000112345555");
        account1.setCreatedDate(Date.valueOf(LocalDate.now().minusMonths(5)));
        account1.setUpdatedDate(Date.valueOf(LocalDate.now().minusMonths(3)));

        int result = accountRepositoryJDBC.edit(account1);

        Account account = accountRepositoryJDBC.findById(uuid);

        assertNotNull(account);
        assertEquals(1, result);
        assertEquals(account1.getAccountNumber(), account.getAccountNumber());
        assertEquals(account1.getStatus(), account.getStatus());
        assertEquals(account1.getCreatedDate(), account.getCreatedDate());
        assertEquals(account1.getUpdatedDate(), account.getUpdatedDate());
    }

    @Test
    public void AccountRepositoryJDBC_FindById_ReturnsModel(){
        UUID uuid = accountRepositoryJDBC.save(account1);

        Account account = accountRepositoryJDBC.findById(uuid);

        assertNotNull(account);
        assertEquals(account1.getAccountNumber(), account.getAccountNumber());
        assertEquals(account1.getStatus(), account.getStatus());
        assertEquals(account1.getCreatedDate(), account.getCreatedDate());
        assertEquals(account1.getUpdatedDate(), account.getUpdatedDate());
    }

    @Test
    public void AccountRepositoryJDBC_DeleteById_DeletesModel(){
        UUID uuid = accountRepositoryJDBC.save(account1);

        accountRepositoryJDBC.deleteById(uuid);

        Account account = accountRepositoryJDBC.findById(uuid);

        assertNull(account);
    }
}
