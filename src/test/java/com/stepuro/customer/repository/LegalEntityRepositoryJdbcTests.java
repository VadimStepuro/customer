package com.stepuro.customer.repository;

import com.stepuro.customer.api.exceptions.ResourceNotFoundException;
import com.stepuro.customer.model.LegalEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static com.stepuro.customer.repository.Samples.LegalEntitySamples.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class LegalEntityRepositoryJdbcTests {
    @Autowired
    private LegalEntityRepositoryJdbc legalEntityRepositoryJdbc;

    @Test
    public void LegalEntityRepositoryJdbc_Save_SavesModel(){
        Integer id = legalEntityRepositoryJdbc.save(legalEntity1);

        LegalEntity savedLegalEntity = legalEntityRepositoryJdbc.findById(id);

        assertNotNull(savedLegalEntity);
        assertEquals(legalEntity1.getName(), savedLegalEntity.getName());
        assertEquals(legalEntity1.getAddress(), savedLegalEntity.getAddress());
        assertEquals(legalEntity1.getCity(), savedLegalEntity.getCity());
        assertEquals(legalEntity1.getInn(), savedLegalEntity.getInn());
        assertEquals(legalEntity1.getCreatedDate().getTime(), savedLegalEntity.getCreatedDate().getTime());
        assertEquals(legalEntity1.getUpdatedDate().getTime(), savedLegalEntity.getUpdatedDate().getTime());
    }

    @Test
    public void LegalEntityRepositoryJdbc_FindById_ReturnsModel(){
        Integer id = legalEntityRepositoryJdbc.save(legalEntity1);
        legalEntityRepositoryJdbc.save(legalEntity2);

        LegalEntity foundLegalEntity = legalEntityRepositoryJdbc.findById(id);

        assertNotNull(foundLegalEntity);
        assertEquals(id, foundLegalEntity.getLegalEntityId());
        assertEquals(legalEntity1.getName(), foundLegalEntity.getName());
        assertEquals(legalEntity1.getAddress(), foundLegalEntity.getAddress());
        assertEquals(legalEntity1.getCity(), foundLegalEntity.getCity());
        assertEquals(legalEntity1.getInn(), foundLegalEntity.getInn());
        assertEquals(legalEntity1.getCreatedDate().getTime(), foundLegalEntity.getCreatedDate().getTime());
        assertEquals(legalEntity1.getUpdatedDate().getTime(), foundLegalEntity.getUpdatedDate().getTime());
    }

    @Test
    public void LegalEntityRepositoryJdbc_Edit_ChangesModel(){
        Integer id = legalEntityRepositoryJdbc.save(legalEntity1);

        LegalEntity savedLegalEntity = legalEntityRepositoryJdbc.findById(id);

        savedLegalEntity.setName("Ivan");
        savedLegalEntity.setAddress("Lenina");
        savedLegalEntity.setCity("Minsk");
        savedLegalEntity.setInn("2222222222");
        savedLegalEntity.setCreatedDate(Timestamp.valueOf(LocalDateTime.now().minusMonths(5)));
        savedLegalEntity.setUpdatedDate(Timestamp.valueOf(LocalDateTime.now().minusMonths(3)));

        legalEntityRepositoryJdbc.edit(savedLegalEntity);

        LegalEntity updatedLegalEntity = legalEntityRepositoryJdbc.findById(savedLegalEntity.getLegalEntityId());

        assertNotNull(updatedLegalEntity);
        assertEquals(savedLegalEntity.getLegalEntityId(), updatedLegalEntity.getLegalEntityId());
        assertEquals(savedLegalEntity.getName(), updatedLegalEntity.getName());
        assertEquals(savedLegalEntity.getAddress(), updatedLegalEntity.getAddress());
        assertEquals(savedLegalEntity.getCity(), updatedLegalEntity.getCity());
        assertEquals(savedLegalEntity.getInn(), updatedLegalEntity.getInn());
        assertEquals(savedLegalEntity.getCreatedDate().getTime(), updatedLegalEntity.getCreatedDate().getTime());
        assertEquals(savedLegalEntity.getUpdatedDate().getTime(), updatedLegalEntity.getUpdatedDate().getTime());
    }

    @Test
    public void LegalEntityRepositoryJdbc_Remove_RemovesModel(){
        Integer id = legalEntityRepositoryJdbc.save(legalEntity1);

        legalEntityRepositoryJdbc.deleteById(id);

        assertThrows(ResourceNotFoundException.class, () -> legalEntityRepositoryJdbc.findById(id));
    }
}
