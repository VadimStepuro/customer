package com.stepuro.customer.service;

import com.stepuro.customer.api.dto.IndividualDto;
import com.stepuro.customer.api.exceptions.ResourceNotFoundException;
import com.stepuro.customer.model.Individual;
import com.stepuro.customer.repository.IndividualRepositoryJpa;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class IndividualServiceTests {
    @Mock
    private IndividualRepositoryJpa individualRepositoryJpa;

    @InjectMocks
    private IndividualService individualService;

    @Test
    public void IndividualService_FindAll_ReturnsAllModels(){
        //Arrange
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

        List<Individual> individuals = List.of(individual1, individual2, individual3, individual4);

        when(individualRepositoryJpa.findAll()).thenReturn(individuals);

        //Act
        List<IndividualDto> individualDtos = individualService.findAll();

        //Assert
        assertNotNull(individualDtos);
        assertEquals(4, individualDtos.size());
    }

    @Test
    public void IndividualService_FindById_ReturnsModel(){
        Individual savedIndividual = Individual.builder()
                .name("Vadim")
                .lastName("Stepuro")
                .address("Kletskova")
                .city("Grodno")
                .dayOfBirth(Date.valueOf(LocalDate.of(2003, 10, 18)))
                .createdDate(Date.valueOf(LocalDate.now()))
                .updatedDate(Date.valueOf(LocalDate.now()))
                .build();

        when(individualRepositoryJpa.findById(any(Integer.class))).thenReturn(Optional.of(savedIndividual));

        IndividualDto individual = individualService.findById(1);

        assertNotNull(individual);
        assertEquals(savedIndividual.getName(), individual.getName());
        assertEquals(savedIndividual.getLastName(), individual.getLastName());
        assertEquals(savedIndividual.getAddress(), individual.getAddress());
        assertEquals(savedIndividual.getCity(), individual.getCity());
        assertEquals(savedIndividual.getDayOfBirth(), individual.getDayOfBirth());
        assertEquals(savedIndividual.getCreatedDate(), individual.getCreatedDate());
        assertEquals(savedIndividual.getUpdatedDate(), individual.getUpdatedDate());
    }

    @Test
    public void IndividualService_Save_ReturnsSavedModel(){
        Individual individual = Individual.builder()
                .name("Vadim")
                .lastName("Stepuro")
                .address("Kletskova")
                .city("Grodno")
                .dayOfBirth(Date.valueOf(LocalDate.of(2003, 10, 18)))
                .createdDate(Date.valueOf(LocalDate.now()))
                .updatedDate(Date.valueOf(LocalDate.now()))
                .build();

        IndividualDto individualDto = IndividualDto.builder()
                .name("Vadim")
                .lastName("Stepuro")
                .address("Kletskova")
                .city("Grodno")
                .dayOfBirth(Date.valueOf(LocalDate.of(2003, 10, 18)))
                .createdDate(Date.valueOf(LocalDate.now()))
                .updatedDate(Date.valueOf(LocalDate.now()))
                .build();

        when(individualRepositoryJpa.save(any(Individual.class))).thenReturn(individual);

        IndividualDto savedIndividual = individualService.create(individualDto);

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
    public void IndividualService_Edit_ReturnsEditedModel(){
        Individual individual1 = Individual.builder()
                .individualId(1)
                .name("Vadim")
                .lastName("Stepuro")
                .address("Kletskova")
                .city("Grodno")
                .dayOfBirth(Date.valueOf(LocalDate.of(2003, 10, 18)))
                .createdDate(Date.valueOf(LocalDate.now()))
                .updatedDate(Date.valueOf(LocalDate.now()))
                .build();
        IndividualDto individualDto = IndividualDto.builder()
                .individualId(1)
                .name("Vadim")
                .lastName("Stepuro")
                .address("Kletskova")
                .city("Grodno")
                .dayOfBirth(Date.valueOf(LocalDate.of(2003, 10, 18)))
                .createdDate(Date.valueOf(LocalDate.now()))
                .updatedDate(Date.valueOf(LocalDate.now()))
                .build();

        when(individualRepositoryJpa.save(any(Individual.class))).thenReturn(individual1);
        when(individualRepositoryJpa.findById(any(Integer.class))).thenReturn(Optional.of(individual1));

        IndividualDto savedIndividual = individualService.create(individualDto);

        savedIndividual.setName("Ivan");
        savedIndividual.setLastName("Ivanov");
        savedIndividual.setAddress("Lenina");
        savedIndividual.setCity("Minsk");
        savedIndividual.setDayOfBirth(Date.valueOf(LocalDate.of(2000, 3, 21)));
        savedIndividual.setCreatedDate(Date.valueOf(LocalDate.now().minusMonths(5)));
        savedIndividual.setUpdatedDate(Date.valueOf(LocalDate.now().minusMonths(3)));

        IndividualDto updatedIndividual = individualService.edit(savedIndividual);

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
    public void IndividualService_Delete_DeletesModel(){
        when(individualRepositoryJpa.findById(any(Integer.class))).thenReturn(Optional.empty());
        doNothing().when(individualRepositoryJpa).deleteById(isA(Integer.class));

        individualService.delete(1);

        assertThrows(ResourceNotFoundException.class, () -> individualService.findById(1));
    }
}
