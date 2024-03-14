package com.stepuro.customer.repository;

import com.stepuro.customer.api.exceptions.ResourceNotFoundException;
import com.stepuro.customer.model.Individual;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.stepuro.customer.repository.Samples.IndividualSamples.individual1;
import static com.stepuro.customer.repository.Samples.IndividualSamples.individual2;
import static org.junit.jupiter.api.Assertions.*;

@JdbcTest
public class IndividualRepositoryJdbcTests {
    private IndividualRepositoryJdbc individualRepositoryJdbc;
    private EmbeddedDatabase embeddedDatabase;

    @BeforeEach
    public void setup(){
        embeddedDatabase = new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2)
                .addScript("/sql/02.create-individual-entity.sql")
                .build();

        individualRepositoryJdbc = new IndividualRepositoryJdbc();
        individualRepositoryJdbc.setDataSource(embeddedDatabase);
    }

    @AfterEach
    public void tearDown(){
        embeddedDatabase.shutdown();
    }

    @Test
    public void IndividualRepositoryJdbc_Save_SavesModel(){
        Integer id = individualRepositoryJdbc.save(individual1);

        Individual savedIndividual = individualRepositoryJdbc.findById(id);

        assertNotNull(savedIndividual);
        assertEquals(individual1.getName(), savedIndividual.getName());
        assertEquals(individual1.getLastName(), savedIndividual.getLastName());
        assertEquals(individual1.getAddress(), savedIndividual.getAddress());
        assertEquals(individual1.getCity(), savedIndividual.getCity());
        assertEquals(individual1.getDayOfBirth(), savedIndividual.getDayOfBirth());
        assertEquals(individual1.getCreatedDate().getTime(), savedIndividual.getCreatedDate().getTime());
        assertEquals(individual1.getUpdatedDate().getTime(), savedIndividual.getUpdatedDate().getTime());
    }

    @Test
    public void IndividualRepositoryJdbc_FindById_ReturnsModel(){
        Integer id = individualRepositoryJdbc.save(individual1);
        individualRepositoryJdbc.save(individual2);

        Individual individual = individualRepositoryJdbc.findById(id);

        assertNotNull(individual);
        assertEquals(id, individual.getIndividualId());
        assertEquals(individual1.getName(), individual.getName());
        assertEquals(individual1.getLastName(), individual.getLastName());
        assertEquals(individual1.getAddress(), individual.getAddress());
        assertEquals(individual1.getCity(), individual.getCity());
        assertEquals(individual1.getDayOfBirth(), individual.getDayOfBirth());
        assertEquals(individual1.getCreatedDate().getTime(), individual.getCreatedDate().getTime());
        assertEquals(individual1.getUpdatedDate().getTime(), individual.getUpdatedDate().getTime());
    }

    @Test
    public void IndividualRepositoryJdbc_Edit_ChangesModel(){
        Integer id = individualRepositoryJdbc.save(individual1);

        Individual savedIndividual = individualRepositoryJdbc.findById(id);

        savedIndividual.setName("Ivan");
        savedIndividual.setLastName("Ivanov");
        savedIndividual.setAddress("Lenina");
        savedIndividual.setCity("Minsk");
        savedIndividual.setDayOfBirth(Date.valueOf(LocalDate.of(2000, 3, 21)));
        savedIndividual.setCreatedDate(Timestamp.valueOf(LocalDateTime.now().minusMonths(5)));
        savedIndividual.setUpdatedDate(Timestamp.valueOf(LocalDateTime.now().minusMonths(3)));

        individualRepositoryJdbc.edit(savedIndividual);

        Individual updatedIndividual = individualRepositoryJdbc.findById(id);

        assertNotNull(updatedIndividual);
        assertEquals(savedIndividual.getIndividualId(), updatedIndividual.getIndividualId());
        assertEquals(savedIndividual.getName(), updatedIndividual.getName());
        assertEquals(savedIndividual.getLastName(), updatedIndividual.getLastName());
        assertEquals(savedIndividual.getAddress(), updatedIndividual.getAddress());
        assertEquals(savedIndividual.getCity(), updatedIndividual.getCity());
        assertEquals(savedIndividual.getDayOfBirth(), updatedIndividual.getDayOfBirth());
        assertEquals(savedIndividual.getCreatedDate().getTime(), updatedIndividual.getCreatedDate().getTime());
        assertEquals(savedIndividual.getUpdatedDate().getTime(), updatedIndividual.getUpdatedDate().getTime());
    }

    @Test
    public void IndividualRepositoryJdbc_Remove_RemovesModel(){
        Integer id = individualRepositoryJdbc.save(individual1);

        individualRepositoryJdbc.deleteById(id);

        assertThrows(ResourceNotFoundException.class, () -> individualRepositoryJdbc.findById(id));
    }
}
