package com.stepuro.customer.repository;

import com.stepuro.customer.model.LegalEntity;
import com.stepuro.customer.utils.aspect.LoggingAspect;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.sql.Date;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
public class LegalEntityRepositoryJpaTests {
    @Autowired
    private LegalEntityRepositoryJpa legalEntityRepositoryJpa;

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

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


        logger.error(savedLegalEntity.getLegalEntityId().toString());
        //Assert
        assertNotNull(savedLegalEntity);
        assertEquals(legalEntity.getName(), savedLegalEntity.getName());
        assertEquals(legalEntity.getAddress(), savedLegalEntity.getAddress());
        assertEquals(legalEntity.getCity(), savedLegalEntity.getCity());
        assertEquals(legalEntity.getInn(), savedLegalEntity.getInn());
        assertEquals(legalEntity.getCreatedDate(), savedLegalEntity.getCreatedDate());
        assertEquals(legalEntity.getUpdatedDate(), savedLegalEntity.getUpdatedDate());
    }
}
