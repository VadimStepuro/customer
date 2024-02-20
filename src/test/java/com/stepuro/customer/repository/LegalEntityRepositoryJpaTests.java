package com.stepuro.customer.repository;

import com.stepuro.customer.model.LegalEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import static com.stepuro.customer.repository.Samples.LegalEntitySamples.*;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class LegalEntityRepositoryJpaTests {
    @Autowired
    private LegalEntityRepositoryJpa legalEntityRepositoryJpa;

    @Test
    public void LegalEntityRepository_Save_ReturnsSavedModel(){
        LegalEntity savedLegalEntity = legalEntityRepositoryJpa.save(legalEntity1);

        assertNotNull(savedLegalEntity);
        assertEquals(legalEntity1.getName(), savedLegalEntity.getName());
        assertEquals(legalEntity1.getAddress(), savedLegalEntity.getAddress());
        assertEquals(legalEntity1.getCity(), savedLegalEntity.getCity());
        assertEquals(legalEntity1.getInn(), savedLegalEntity.getInn());
        assertEquals(legalEntity1.getCreatedDate(), savedLegalEntity.getCreatedDate());
        assertEquals(legalEntity1.getUpdatedDate(), savedLegalEntity.getUpdatedDate());
    }

    @Test
    public void LegalEntityRepository_FindAll_ReturnsAllModels(){
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
        LegalEntity savedLegalEntity = legalEntityRepositoryJpa.save(legalEntity1);

        savedLegalEntity.setName("Ivan");
        savedLegalEntity.setAddress("Lenina");
        savedLegalEntity.setCity("Minsk");
        savedLegalEntity.setInn("2222222222");
        savedLegalEntity.setCreatedDate(Timestamp.valueOf(LocalDateTime.now().minusMonths(5)));
        savedLegalEntity.setUpdatedDate(Timestamp.valueOf(LocalDateTime.now().minusMonths(3)));

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
        LegalEntity savedLegalEntity = legalEntityRepositoryJpa.save(legalEntity1);

        legalEntityRepositoryJpa.deleteById(savedLegalEntity.getLegalEntityId());

        boolean existsById = legalEntityRepositoryJpa.existsById(savedLegalEntity.getLegalEntityId());

        assertFalse(existsById);
    }
}
