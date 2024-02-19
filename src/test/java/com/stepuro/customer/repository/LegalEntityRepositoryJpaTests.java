package com.stepuro.customer.repository;

import com.stepuro.customer.model.LegalEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class LegalEntityRepositoryJpaTests {
    @Autowired
    private LegalEntityRepositoryJpa legalEntityRepositoryJpa;

    @Test
    public void LegalEntityRepository_Save_ReturnsSavedModel(){
        //Arrange
        LegalEntity legalEntity = LegalEntity.builder()
                .name("Vadim")
                .address("Kletskova")
                .city("Grodno")
                .inn("1111111111")
                .createdDate(Date.valueOf(LocalDate.now()))
                .updatedDate(Date.valueOf(LocalDate.now()))
                .build();

        //Act
        LegalEntity savedLegalEntity = legalEntityRepositoryJpa.save(legalEntity);

        //Assert
        assertNotNull(savedLegalEntity);
        assertEquals(legalEntity.getName(), savedLegalEntity.getName());
        assertEquals(legalEntity.getAddress(), savedLegalEntity.getAddress());
        assertEquals(legalEntity.getCity(), savedLegalEntity.getCity());
        assertEquals(legalEntity.getInn(), savedLegalEntity.getInn());
        assertEquals(legalEntity.getCreatedDate(), savedLegalEntity.getCreatedDate());
        assertEquals(legalEntity.getUpdatedDate(), savedLegalEntity.getUpdatedDate());
    }

    @Test
    public void LegalEntityRepository_FindAll_ReturnsAllModels(){
        LegalEntity legalEntity1 = LegalEntity.builder()
                .name("Vadim")
                .address("Kletskova")
                .city("Grodno")
                .inn("1111111111")
                .createdDate(Date.valueOf(LocalDate.now()))
                .updatedDate(Date.valueOf(LocalDate.now()))
                .build();
        LegalEntity legalEntity2 = LegalEntity.builder()
                .name("Yagor")
                .address("Yanka Kupala")
                .city("Grodno")
                .inn("2222222222")
                .createdDate(Date.valueOf(LocalDate.now().minusMonths(5)))
                .updatedDate(Date.valueOf(LocalDate.now().minusMonths(3)))
                .build();
        LegalEntity legalEntity3 = LegalEntity.builder()
                .name("Konstantin")
                .address("Masherova")
                .city("Minsk")
                .inn("3333333333")
                .createdDate(Date.valueOf(LocalDate.now().minusMonths(6)))
                .updatedDate(Date.valueOf(LocalDate.now().minusMonths(4)))
                .build();
        LegalEntity legalEntity4 = LegalEntity.builder()
                .name("Ilia")
                .address("Lenina")
                .city("Gomel")
                .inn("4444444444")
                .createdDate(Date.valueOf(LocalDate.now().minusMonths(7)))
                .updatedDate(Date.valueOf(LocalDate.now().minusMonths(5)))
                .build();

        legalEntityRepositoryJpa.save(legalEntity1);
        legalEntityRepositoryJpa.save(legalEntity2);
        legalEntityRepositoryJpa.save(legalEntity3);
        legalEntityRepositoryJpa.save(legalEntity4);

        List<LegalEntity> legalEntities = legalEntityRepositoryJpa.findAll();

        assertNotNull(legalEntities);
        assertEquals(4, legalEntities.size());
    }

    @Test
    public void LegalEntityRepository_FindById_ReturnsModel(){
        LegalEntity legalEntity1 = LegalEntity.builder()
                .name("Vadim")
                .address("Kletskova")
                .city("Grodno")
                .inn("1111111111")
                .createdDate(Date.valueOf(LocalDate.now()))
                .updatedDate(Date.valueOf(LocalDate.now()))
                .build();
        LegalEntity legalEntity2 = LegalEntity.builder()
                .name("Yagor")
                .address("Yanka Kupala")
                .city("Grodno")
                .inn("2222222222")
                .createdDate(Date.valueOf(LocalDate.now().minusMonths(5)))
                .updatedDate(Date.valueOf(LocalDate.now().minusMonths(3)))
                .build();

        LegalEntity savedLegalEntity = legalEntityRepositoryJpa.save(legalEntity1);
        legalEntityRepositoryJpa.save(legalEntity2);


        LegalEntity legalEntity = legalEntityRepositoryJpa.findById(savedLegalEntity.getLegalEntityId()).get();

        assertNotNull(legalEntity);
        assertEquals(savedLegalEntity.getLegalEntityId(), legalEntity.getLegalEntityId());
        assertEquals(savedLegalEntity.getName(), legalEntity.getName());
        assertEquals(savedLegalEntity.getAddress(), legalEntity.getAddress());
        assertEquals(savedLegalEntity.getCity(), legalEntity.getCity());
        assertEquals(savedLegalEntity.getInn(), legalEntity.getInn());
        assertEquals(savedLegalEntity.getCreatedDate(), legalEntity.getCreatedDate());
        assertEquals(savedLegalEntity.getUpdatedDate(), legalEntity.getUpdatedDate());
    }

    @Test
    public void LegalEntityRepository_Update_ChangesModel(){
        LegalEntity legalEntity1 = LegalEntity.builder()
                .name("Vadim")
                .address("Kletskova")
                .city("Grodno")
                .inn("1111111111")
                .createdDate(Date.valueOf(LocalDate.now()))
                .updatedDate(Date.valueOf(LocalDate.now()))
                .build();


        LegalEntity savedLegalEntity = legalEntityRepositoryJpa.save(legalEntity1);

        savedLegalEntity.setName("Ivan");
        savedLegalEntity.setAddress("Lenina");
        savedLegalEntity.setCity("Minsk");
        savedLegalEntity.setInn("2222222222");
        savedLegalEntity.setCreatedDate(Date.valueOf(LocalDate.now().minusMonths(5)));
        savedLegalEntity.setUpdatedDate(Date.valueOf(LocalDate.now().minusMonths(3)));

        LegalEntity updatedLegalEntity = legalEntityRepositoryJpa.save(savedLegalEntity);

        assertNotNull(updatedLegalEntity);
        assertEquals(savedLegalEntity.getLegalEntityId(), updatedLegalEntity.getLegalEntityId());
        assertEquals(savedLegalEntity.getName(), updatedLegalEntity.getName());
        assertEquals(savedLegalEntity.getAddress(), updatedLegalEntity.getAddress());
        assertEquals(savedLegalEntity.getCity(), updatedLegalEntity.getCity());
        assertEquals(savedLegalEntity.getInn(), updatedLegalEntity.getInn());
        assertEquals(savedLegalEntity.getCreatedDate(), updatedLegalEntity.getCreatedDate());
        assertEquals(savedLegalEntity.getUpdatedDate(), updatedLegalEntity.getUpdatedDate());
    }

    @Test
    public void IndividualRepository_Remove_RemovesModel(){
        LegalEntity legalEntity1 = LegalEntity.builder()
                .name("Vadim")
                .address("Kletskova")
                .city("Grodno")
                .inn("1111111111")
                .createdDate(Date.valueOf(LocalDate.now()))
                .updatedDate(Date.valueOf(LocalDate.now()))
                .build();

        LegalEntity savedLegalEntity = legalEntityRepositoryJpa.save(legalEntity1);

        legalEntityRepositoryJpa.deleteById(savedLegalEntity.getLegalEntityId());

        boolean existsById = legalEntityRepositoryJpa.existsById(savedLegalEntity.getLegalEntityId());

        assertFalse(existsById);
    }
}
