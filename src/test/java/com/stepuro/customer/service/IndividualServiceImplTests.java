package com.stepuro.customer.service;

import com.stepuro.customer.api.dto.IndividualDto;
import com.stepuro.customer.api.exceptions.ResourceNotFoundException;
import com.stepuro.customer.model.Individual;
import com.stepuro.customer.repository.IndividualRepositoryJpa;
import com.stepuro.customer.service.Impl.IndividualServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.stepuro.customer.service.samples.IndividualSamples.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class IndividualServiceImplTests {
    @Mock
    private IndividualRepositoryJpa individualRepositoryJpa;

    @InjectMocks
    private IndividualServiceImpl individualServiceImpl;

    @Test
    public void IndividualService_FindAll_ReturnsAllModels(){
        when(individualRepositoryJpa.findAll()).thenReturn(individualList);

        List<IndividualDto> individualDtos = individualServiceImpl.findAll();

        assertNotNull(individualDtos);
        assertEquals(4, individualDtos.size());
    }

    @Test
    public void IndividualService_FindById_ReturnsModel(){
        when(individualRepositoryJpa.findById(any(Integer.class))).thenReturn(Optional.of(individual1));

        IndividualDto individual = individualServiceImpl.findById(1);

        assertNotNull(individual);
        assertEquals(individual1.getName(), individual.getName());
        assertEquals(individual1.getLastName(), individual.getLastName());
        assertEquals(individual1.getAddress(), individual.getAddress());
        assertEquals(individual1.getCity(), individual.getCity());
        assertEquals(individual1.getDayOfBirth(), individual.getDayOfBirth());
        assertEquals(individual1.getCreatedDate(), individual.getCreatedDate());
        assertEquals(individual1.getUpdatedDate(), individual.getUpdatedDate());
    }

    @Test
    public void IndividualService_Save_ReturnsSavedModel(){
        when(individualRepositoryJpa.save(any(Individual.class))).thenReturn(individual1);

        IndividualDto savedIndividual = individualServiceImpl.create(individualDto);

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
    public void IndividualService_Edit_ReturnsEditedModel(){
        when(individualRepositoryJpa.save(any(Individual.class))).thenReturn(individual1);
        when(individualRepositoryJpa.findById(any(Integer.class))).thenReturn(Optional.of(individual1));

        IndividualDto savedIndividual = individualServiceImpl.create(individualDto);

        savedIndividual.setName("Ivan");
        savedIndividual.setLastName("Ivanov");
        savedIndividual.setAddress("Lenina");
        savedIndividual.setCity("Minsk");
        savedIndividual.setDayOfBirth(Date.valueOf(LocalDate.of(2000, 3, 21)));
        savedIndividual.setCreatedDate(Timestamp.valueOf(LocalDateTime.now().minusMonths(5)));
        savedIndividual.setUpdatedDate(Timestamp.valueOf(LocalDateTime.now().minusMonths(3)));

        IndividualDto updatedIndividual = individualServiceImpl.edit(savedIndividual);

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

        individualServiceImpl.delete(1);

        assertThrows(ResourceNotFoundException.class, () -> individualServiceImpl.findById(1));
    }
}
