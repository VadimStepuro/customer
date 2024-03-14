package com.stepuro.customer.repository.Samples;

import com.stepuro.customer.model.LegalEntity;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

public class LegalEntitySamples {
    public static LegalEntity legalEntity1 = LegalEntity.builder()
            .name("Vadim")
            .address("Kletskova")
            .city("Grodno")
            .inn("1111111111")
            .createdDate(Timestamp.from(Instant.now()))
            .updatedDate(Timestamp.from(Instant.now()))
            .build();
    public static LegalEntity legalEntity2 = LegalEntity.builder()
            .name("Yagor")
            .address("Yanka Kupala")
            .city("Grodno")
            .inn("2222222222")
            .createdDate(Timestamp.from(Instant.now()))
            .updatedDate(Timestamp.from(Instant.now()))
            .build();
    public static LegalEntity legalEntity3 = LegalEntity.builder()
            .name("Konstantin")
            .address("Masherova")
            .city("Minsk")
            .inn("3333333333")
            .createdDate(Timestamp.from(Instant.now()))
            .updatedDate(Timestamp.from(Instant.now()))
            .build();
    public static LegalEntity legalEntity4 = LegalEntity.builder()
            .name("Ilia")
            .address("Lenina")
            .city("Gomel")
            .inn("4444444444")
            .createdDate(Timestamp.from(Instant.now()))
            .updatedDate(Timestamp.from(Instant.now()))
            .build();

    public static List<LegalEntity> legalEntityList = List.of(legalEntity1, legalEntity2, legalEntity3, legalEntity4);
}
