package com.stepuro.customer.repository;

import com.stepuro.customer.model.Individual;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.stepuro.customer.repository.Samples.IndividualSamples.*;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class IndividualRepositoryJpaTests {
    @Autowired
    private IndividualRepositoryJpa individualRepositoryJpa;

    @Test
    public void IndividualRepository_Save_ReturnsSavedModel(){
        Individual savedIndividual = individualRepositoryJpa.save(individual1);

        assertNotNull(savedIndividual);
        assertEquals(individual1.getName(), savedIndividual.getName());
        assertEquals(individual1.getLastName(), savedIndividual.getLastName());
        assertEquals(individual1.getAddress(), savedIndividual.getAddress());
        assertEquals(individual1.getCity(), savedIndividual.getCity());
        assertEquals(individual1.getDayOfBirth(), savedIndividual.getDayOfBirth());
        assertEquals(individual1.getCreatedDate(), savedIndividual.getCreatedDate());
        assertEquals(individual1.getUpdatedDate(), savedIndividual.getUpdatedDate());
    }

    @Test
    public void IndividualRepository_FindAll_ReturnsAllModels(){
        List<Individual> individuals = individualRepositoryJpa.saveAll(individualList);


        assertNotNull(individuals);
        assertEquals(4, individuals.size());
    }

    @Test
    public void IndividualRepository_FindById_ReturnsModel(){
        Individual savedIndividual = individualRepositoryJpa.save(individual1);
        individualRepositoryJpa.save(individual2);


        Individual individual = individualRepositoryJpa.findById(savedIndividual.getIndividualId()).get();

        assertNotNull(individual);
        assertEquals(savedIndividual.getIndividualId(), individual.getIndividualId());
        assertEquals(savedIndividual.getName(), individual.getName());
        assertEquals(savedIndividual.getLastName(), individual.getLastName());
        assertEquals(savedIndividual.getAddress(), individual.getAddress());
        assertEquals(savedIndividual.getCity(), individual.getCity());
        assertEquals(savedIndividual.getDayOfBirth(), individual.getDayOfBirth());
        assertEquals(savedIndividual.getCreatedDate(), individual.getCreatedDate());
        assertEquals(savedIndividual.getUpdatedDate(), individual.getUpdatedDate());
    }

    @Test
    public void IndividualRepository_Update_ChangesModel(){
        Individual savedIndividual = individualRepositoryJpa.save(individual1);

        savedIndividual.setName("Ivan");
        savedIndividual.setLastName("Ivanov");
        savedIndividual.setAddress("Lenina");
        savedIndividual.setCity("Minsk");
        savedIndividual.setDayOfBirth(Date.valueOf(LocalDate.of(2000, 3, 21)));
        savedIndividual.setCreatedDate(Timestamp.valueOf(LocalDateTime.now().minusMonths(5)));
        savedIndividual.setUpdatedDate(Timestamp.valueOf(LocalDateTime.now().minusMonths(3)));

        Individual updatedIndividual = individualRepositoryJpa.save(savedIndividual);

        assertNotNull(updatedIndividual);
        assertEquals(savedIndividual.getIndividualId(), updatedIndividual.getIndividualId());
        assertEquals(savedIndividual.getName(), updatedIndividual.getName());
        assertEquals(savedIndividual.getLastName(), updatedIndividual.getLastName());
        assertEquals(savedIndividual.getAddress(), updatedIndividual.getAddress());
        assertEquals(savedIndividual.getCity(), updatedIndividual.getCity());
        assertEquals(savedIndividual.getDayOfBirth(), updatedIndividual.getDayOfBirth());
        assertEquals(savedIndividual.getCreatedDate(), updatedIndividual.getCreatedDate());
        assertEquals(savedIndividual.getUpdatedDate(), updatedIndividual.getUpdatedDate());
    }

    @Test
    public void IndividualRepository_Remove_RemovesModel(){
        Individual savedIndividual = individualRepositoryJpa.save(individual1);

        individualRepositoryJpa.deleteById(savedIndividual.getIndividualId());

        boolean existsById = individualRepositoryJpa.existsById(savedIndividual.getIndividualId());

        assertFalse(existsById);
    }
}
