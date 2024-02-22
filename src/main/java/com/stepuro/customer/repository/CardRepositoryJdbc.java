package com.stepuro.customer.repository;

import com.stepuro.customer.model.Card;
import com.stepuro.customer.model.Individual;
import com.stepuro.customer.model.enums.CardStatus;
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
public class CardRepositoryJdbc {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Transactional
    public UUID save(Card card){
        card.setId(UUID.randomUUID());
        jdbcTemplate.update("INSERT INTO card " +
                        "(id, card_number, account_number, created_date, updated_date, status, expiry_date, balance, individual_id) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)",
                card.getId(),
                card.getCardNumber(),
                card.getAccountNumber(),
                card.getCreatedDate().toString(),
                card.getUpdatedDate().toString(),
                card.getStatus().toString(),
                card.getExpiryDate(),
                card.getBalance(),
                card.getIndividual() == null ? null : card.getIndividual());

        return card.getId();
    }

    @Transactional
    public int edit(Card card){
        return jdbcTemplate.update("UPDATE card " +
                        "SET card_number = ?, " +
                        "account_number = ?, " +
                        "created_date = ?, " +
                        "updated_date = ?, " +
                        "status = ?, " +
                        "expiry_date = ?, " +
                        "individual_id = ?, " +
                        "balance = ? " +
                        "WHERE card.id = ? ",
                card.getCardNumber(),
                card.getAccountNumber(),
                card.getCreatedDate().toString(),
                card.getUpdatedDate().toString(),
                card.getStatus().toString(),
                card.getExpiryDate(),
                card.getIndividual() == null ? null : card.getIndividual().getIndividualId(),
                card.getBalance(),
                card.getId());
    }

    public List<Card> findAll(){
        return jdbcTemplate.query("SELECT * " +
                        "FROM card " +
                        "LEFT JOIN individual " +
                        "ON account.individual = individual.individual_id",
                new CardMapper());
    }

    public Card findById(UUID id){
        try {
            return jdbcTemplate.queryForObject("SELECT * " +
                            "FROM card " +
                            "LEFT JOIN individual " +
                            "ON card.individual_id = individual.individual_id " +
                            "WHERE card.id = ?",
                    new CardMapper(),
                    id);
        }
        catch (EmptyResultDataAccessException exception){
            return null;
        }
    }

    @Transactional
    public void deleteById(UUID id){
        jdbcTemplate.update("DELETE FROM card where id = ?", id);
    }

    private static final class CardMapper implements RowMapper<Card> {

        @Override
        public Card mapRow(ResultSet rs, int rowNum) throws SQLException {
            Individual individual = Individual.builder()
                    .individualId(rs.getInt("individual_id"))
                    .name(rs.getString("name"))
                    .lastName(rs.getString("last_name"))
                    .dayOfBirth(rs.getDate("day_of_birth"))
                    .address(rs.getString("address"))
                    .city(rs.getString("city"))
                    .createdDate(rs.getTimestamp("individual.created_date"))
                    .updatedDate(rs.getTimestamp("individual.updated_date"))
                    .build();

            Card card = new Card();
            card.setId(UUID.fromString(rs.getString("id")));
            card.setCardNumber(rs.getString("card_number"));
            card.setAccountNumber(rs.getString("account_number"));
            card.setCreatedDate(rs.getTimestamp("card.created_date"));
            card.setUpdatedDate(rs.getTimestamp("card.updated_date"));
            card.setStatus(CardStatus.valueOf(rs.getString("status")));
            card.setBalance(rs.getBigDecimal("balance"));
            card.setExpiryDate(rs.getDate("expiry_date"));

            if(rs.getInt("individual_id") == 0)
                card.setIndividual(null);
            else
                card.setIndividual(individual);

            return card;
        }
    }
}
