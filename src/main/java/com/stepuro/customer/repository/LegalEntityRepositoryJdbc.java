package com.stepuro.customer.repository;

import com.stepuro.customer.api.exceptions.ResourceNotFoundException;
import com.stepuro.customer.model.LegalEntity;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@Repository
public class LegalEntityRepositoryJdbc {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Integer save(LegalEntity legalEntity){
        KeyHolder keyHolder = new GeneratedKeyHolder();

        String INSERT_MESSAGE = "INSERT INTO legal_entity " +
                "(legal_entity_id, \"name\", inn, created_date, updated_date, address, city) " +
                "VALUES (nextval('legal_entity_id_sequence'), ?, ?, ?, ?, ?, ?)";

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement(INSERT_MESSAGE, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, legalEntity.getName());
            ps.setString(2, legalEntity.getInn());
            ps.setTimestamp(3, legalEntity.getCreatedDate());
            ps.setTimestamp(4, legalEntity.getUpdatedDate());
            ps.setString(5, legalEntity.getAddress());
            ps.setString(6, legalEntity.getCity());

            return ps;
        }, keyHolder);

        return keyHolder.getKeyAs(Integer.class);
    }

    public int edit(LegalEntity legalEntity){
        return jdbcTemplate.update("UPDATE legal_entity " +
                        "SET \"name\" = ?, " +
                        "inn = ?, " +
                        "created_date = ?, " +
                        "updated_date = ?, " +
                        "address = ?, " +
                        "city = ? " +
                        "WHERE legal_entity_id = ? ",
                legalEntity.getName(),
                legalEntity.getInn(),
                legalEntity.getCreatedDate().toString(),
                legalEntity.getUpdatedDate().toString(),
                legalEntity.getAddress(),
                legalEntity.getCity(),
                legalEntity.getLegalEntityId());
    }

    public List<LegalEntity> findAll(){
        return jdbcTemplate.query("SELECT * " +
                        "FROM legal_entity ",
                new IndividualMapper());
    }

    public LegalEntity findById(Integer id){
        try {
            return jdbcTemplate.queryForObject("SELECT * " +
                            "FROM legal_entity " +
                            "WHERE legal_entity_id = ?",
                    new IndividualMapper(),
                    id);
        }
        catch (EmptyResultDataAccessException exception){
            throw new ResourceNotFoundException("Individual with id " + id + " not found");
        }
    }

    @Transactional
    public void deleteById(Integer id){
        jdbcTemplate.update("DELETE FROM legal_entity where legal_entity_id = ?", id);
    }

    private static final class IndividualMapper implements RowMapper<LegalEntity> {

        @Override
        public LegalEntity mapRow(ResultSet rs, int rowNum) throws SQLException {

            return LegalEntity.builder()
                    .legalEntityId(rs.getInt("legal_entity_id"))
                    .name(rs.getString("name"))
                    .inn(rs.getString("inn"))
                    .address(rs.getString("address"))
                    .city(rs.getString("city"))
                    .createdDate(rs.getTimestamp("created_date"))
                    .updatedDate(rs.getTimestamp("updated_date"))
                    .build();
        }
    }
}
