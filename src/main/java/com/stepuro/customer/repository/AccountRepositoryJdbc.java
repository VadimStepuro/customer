package com.stepuro.customer.repository;

import com.stepuro.customer.model.Account;
import com.stepuro.customer.model.LegalEntity;
import com.stepuro.customer.model.enums.AccountStatus;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@Repository
public class AccountRepositoryJdbc {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Transactional
    public UUID save(Account account){
        account.setId(UUID.randomUUID());
        jdbcTemplate.update("INSERT INTO account " +
                        "(id, account_number, created_date, updated_date, status, legal_entity_id) " +
                        "VALUES (?, ?, ?, ?, ?, ?)",
                account.getId(),
                account.getAccountNumber(),
                account.getCreatedDate().toString(),
                account.getUpdatedDate().toString(),
                account.getStatus().toString(),
                account.getLegalEntity() == null ? null : account.getLegalEntity().getLegalEntityId());

        return account.getId();
    }

    @Transactional
    public int edit(Account account){
        return jdbcTemplate.update("UPDATE account " +
                "SET account_number = ?, " +
                "created_date = ?, " +
                "updated_date = ?, " +
                "status = ?, " +
                "legal_entity_id = ? " +
                "WHERE id = ? ",
                account.getAccountNumber(),
                account.getCreatedDate().toString(),
                account.getUpdatedDate().toString(),
                account.getStatus().toString(),
                account.getLegalEntity() == null ? null : account.getLegalEntity().getLegalEntityId(),
                account.getId());
    }

    public List<Account> findAll(){
        return jdbcTemplate.query("SELECT * " +
                "FROM account " +
                "LEFT JOIN legal_entity " +
                "ON account.legal_entity_id = legal_entity.legal_entity_id",
                new AccountMapper());
    }

    public Account findById(UUID id){
        try {
            return jdbcTemplate.queryForObject("SELECT * " +
                            "FROM account " +
                            "LEFT JOIN legal_entity " +
                            "ON account.legal_entity_id = legal_entity.legal_entity_id " +
                            "WHERE account.id = ?",
                    new AccountMapper(),
                    id);
        }
        catch (EmptyResultDataAccessException exception){
            return null;
        }
    }

    @Transactional
    public void deleteById(UUID id){
        jdbcTemplate.update("DELETE FROM account where id = ?", id);
    }

    private static final class AccountMapper implements RowMapper<Account>{

        @Override
        public Account mapRow(ResultSet rs, int rowNum) throws SQLException {
            LegalEntity legalEntity = LegalEntity.builder()
                    .legalEntityId(rs.getInt("legal_entity_id"))
                    .name(rs.getString("name"))
                    .address(rs.getString("address"))
                    .city(rs.getString("city"))
                    .inn(rs.getString("inn"))
                    .createdDate(rs.getTimestamp(8))
                    .updatedDate(rs.getTimestamp(9))
                    .build();



            Account account = new Account();
            account.setId(UUID.fromString(rs.getString("id")));
            account.setAccountNumber(rs.getString("account_number"));
            account.setCreatedDate(rs.getTimestamp(2));
            account.setUpdatedDate(rs.getTimestamp(3));
            account.setStatus(AccountStatus.valueOf(rs.getString("status")));
            account.setLegalEntity(legalEntity);

            if(rs.getInt("legal_entity_id") == 0)
                account.setLegalEntity(null);
            else
                account.setLegalEntity(legalEntity);

            return account;
        }
    }
}


