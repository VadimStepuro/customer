package com.stepuro.customer.repository;

import com.stepuro.customer.model.Individual;
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
public class IndividualRepositoryJdbc {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Integer save(Individual individual){
        KeyHolder keyHolder = new GeneratedKeyHolder();

        String INSERT_MESSAGE = "INSERT INTO individual " +
                "(individual_id, name, last_name, created_date, updated_date, address, city, day_of_birth) " +
                "VALUES (nextval('individual_id_sequence'), ?, ?, ?, ?, ?, ?, ?)";

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement(INSERT_MESSAGE, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, individual.getName());
            ps.setString(2, individual.getLastName());
            ps.setTimestamp(3, individual.getCreatedDate());
            ps.setTimestamp(4, individual.getUpdatedDate());
            ps.setString(5, individual.getAddress());
            ps.setString(6, individual.getCity());
            ps.setDate(7, individual.getDayOfBirth());

            return ps;
        }, keyHolder);

        return keyHolder.getKeyAs(Integer.class);
    }

    public int edit(Individual individual){
        return jdbcTemplate.update("UPDATE individual " +
                        "SET name = ?, " +
                        "last_name = ?, " +
                        "created_date = ?, " +
                        "updated_date = ?, " +
                        "address = ?, " +
                        "city = ?, " +
                        "day_of_birth = ? " +
                        "WHERE individual_id = ? ",
                individual.getName(),
                individual.getLastName(),
                individual.getCreatedDate().toString(),
                individual.getUpdatedDate().toString(),
                individual.getAddress(),
                individual.getCity(),
                individual.getDayOfBirth(),
                individual.getIndividualId());
    }

    public List<Individual> findAll(){
        return jdbcTemplate.query("SELECT * " +
                        "FROM individual ",
                new IndividualMapper());
    }

    public Individual findById(Integer id){
        try {
            return jdbcTemplate.queryForObject("SELECT * " +
                            "FROM individual " +
                            "WHERE individual_id = ?",
                    new IndividualMapper(),
                    id);
        }
        catch (EmptyResultDataAccessException exception){
            return null;
        }
    }

    @Transactional
    public void deleteById(Integer id){
        jdbcTemplate.update("DELETE FROM individual where individual_id = ?", id);
    }

    private static final class IndividualMapper implements RowMapper<Individual> {

        @Override
        public Individual mapRow(ResultSet rs, int rowNum) throws SQLException {

            return Individual.builder()
                    .individualId(rs.getInt("individual_id"))
                    .name(rs.getString("name"))
                    .lastName(rs.getString("last_name"))
                    .dayOfBirth(rs.getDate("day_of_birth"))
                    .address(rs.getString("address"))
                    .city(rs.getString("city"))
                    .createdDate(rs.getTimestamp("created_date"))
                    .updatedDate(rs.getTimestamp("updated_date"))
                    .build();
        }
    }
}
