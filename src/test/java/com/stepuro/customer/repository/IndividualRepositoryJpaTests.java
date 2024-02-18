package com.stepuro.customer.repository;

import com.stepuro.customer.model.Individual;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class IndividualRepositoryJpaTests {
    @Autowired
    private IndividualRepositoryJpa individualRepositoryJpa;

    @Test
    public void IndividualRepository_Save_ReturnsSavedModel(){
        //Arrange
        Individual individual = Individual.builder()
                .name("Vadim")
                .lastName("Stepuro")
                .address("Kletskova")
                .city("Grodno")
                .dayOfBirth(Date.valueOf(LocalDate.of(2003, 10, 18)))
                .createdDate(Date.valueOf(LocalDate.now()))
                .updatedDate(Date.valueOf(LocalDate.now()))
                .build();

        //Act
        Individual savedIndividual = individualRepositoryJpa.save(individual);

        //Assert
        assertNotNull(savedIndividual);
        assertEquals(individual.getName(), savedIndividual.getName());
        assertEquals(individual.getLastName(), savedIndividual.getLastName());
        assertEquals(individual.getAddress(), savedIndividual.getAddress());
        assertEquals(individual.getCity(), savedIndividual.getCity());
        assertEquals(individual.getDayOfBirth(), savedIndividual.getDayOfBirth());
        assertEquals(individual.getCreatedDate(), savedIndividual.getCreatedDate());
        assertEquals(individual.getUpdatedDate(), savedIndividual.getUpdatedDate());
    }

    @Test
    public void IndividualRepository_FindAll_ReturnsAllModels(){
        Individual individual1 = Individual.builder()
                .name("Vadim")
                .lastName("Stepuro")
                .address("Kletskova")
                .city("Grodno")
                .dayOfBirth(Date.valueOf(LocalDate.of(2003, 10, 18)))
                .createdDate(Date.valueOf(LocalDate.now()))
                .updatedDate(Date.valueOf(LocalDate.now()))
                .build();
        Individual individual2 = Individual.builder()
                .name("Vadim")
                .lastName("Stepuro")
                .address("Kletskova")
                .city("Grodno")
                .dayOfBirth(Date.valueOf(LocalDate.of(2003, 10, 18)))
                .createdDate(Date.valueOf(LocalDate.now()))
                .updatedDate(Date.valueOf(LocalDate.now()))
                .build();
        Individual individual3 = Individual.builder()
                .name("Vadim")
                .lastName("Stepuro")
                .address("Kletskova")
                .city("Grodno")
                .dayOfBirth(Date.valueOf(LocalDate.of(2003, 10, 18)))
                .createdDate(Date.valueOf(LocalDate.now()))
                .updatedDate(Date.valueOf(LocalDate.now()))
                .build();
        Individual individual4 = Individual.builder()
                .name("Vadim")
                .lastName("Stepuro")
                .address("Kletskova")
                .city("Grodno")
                .dayOfBirth(Date.valueOf(LocalDate.of(2003, 10, 18)))
                .createdDate(Date.valueOf(LocalDate.now()))
                .updatedDate(Date.valueOf(LocalDate.now()))
                .build();

        individualRepositoryJpa.save(individual1);
        individualRepositoryJpa.save(individual2);
        individualRepositoryJpa.save(individual3);
        individualRepositoryJpa.save(individual4);

        List<Individual> individuals = individualRepositoryJpa.findAll();

        assertNotNull(individuals);
        assertEquals(4, individuals.size());
    }

    @Test
    public void IndividualRepository_FindById_ReturnsModel(){
        Individual individual1 = Individual.builder()
                .name("Vadim")
                .lastName("Stepuro")
                .address("Kletskova")
                .city("Grodno")
                .dayOfBirth(Date.valueOf(LocalDate.of(2003, 10, 18)))
                .createdDate(Date.valueOf(LocalDate.now()))
                .updatedDate(Date.valueOf(LocalDate.now()))
                .build();
        Individual individual2 = Individual.builder()
                .name("Konstantin")
                .lastName("Ivanov")
                .address("Lenina")
                .city("Minsk")
                .dayOfBirth(Date.valueOf(LocalDate.of(2001, 1, 6)))
                .createdDate(Date.valueOf(LocalDate.now()))
                .updatedDate(Date.valueOf(LocalDate.now()))
                .build();

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
        Individual individual1 = Individual.builder()
                .name("Vadim")
                .lastName("Stepuro")
                .address("Kletskova")
                .city("Grodno")
                .dayOfBirth(Date.valueOf(LocalDate.of(2003, 10, 18)))
                .createdDate(Date.valueOf(LocalDate.now()))
                .updatedDate(Date.valueOf(LocalDate.now()))
                .build();


        Individual savedIndividual = individualRepositoryJpa.save(individual1);

        savedIndividual.setName("Ivan");
        savedIndividual.setLastName("Ivanov");
        savedIndividual.setAddress("Lenina");
        savedIndividual.setCity("Minsk");
        savedIndividual.setDayOfBirth(Date.valueOf(LocalDate.of(2000, 3, 21)));
        savedIndividual.setCreatedDate(Date.valueOf(LocalDate.now().minusMonths(5)));
        savedIndividual.setUpdatedDate(Date.valueOf(LocalDate.now().minusMonths(3)));

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
        Individual individual1 = Individual.builder()
                .name("Vadim")
                .lastName("Stepuro")
                .address("Kletskova")
                .city("Grodno")
                .dayOfBirth(Date.valueOf(LocalDate.of(2003, 10, 18)))
                .createdDate(Date.valueOf(LocalDate.now()))
                .updatedDate(Date.valueOf(LocalDate.now()))
                .build();

        Individual savedIndividual = individualRepositoryJpa.save(individual1);

        individualRepositoryJpa.deleteById(savedIndividual.getIndividualId());

        boolean existsById = individualRepositoryJpa.existsById(savedIndividual.getIndividualId());

        assertFalse(existsById);
    }
}
