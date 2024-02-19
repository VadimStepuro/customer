package com.stepuro.customer.repository.Samples;

import com.stepuro.customer.model.LegalEntity;

import java.sql.Date;
import java.time.LocalDate;

public class LegalEntitySamples {
    public static LegalEntity legalEntity1 = LegalEntity.builder()
            .name("Vadim")
            .address("Kletskova")
            .city("Grodno")
            .inn("1111111111")
            .createdDate(Date.valueOf(LocalDate.now()))
            .updatedDate(Date.valueOf(LocalDate.now()))
            .build();
    public static LegalEntity legalEntity2 = LegalEntity.builder()
            .name("Yagor")
            .address("Yanka Kupala")
            .city("Grodno")
            .inn("2222222222")
            .createdDate(Date.valueOf(LocalDate.now().minusMonths(5)))
            .updatedDate(Date.valueOf(LocalDate.now().minusMonths(3)))
            .build();
    public static LegalEntity legalEntity3 = LegalEntity.builder()
            .name("Konstantin")
            .address("Masherova")
            .city("Minsk")
            .inn("3333333333")
            .createdDate(Date.valueOf(LocalDate.now().minusMonths(6)))
            .updatedDate(Date.valueOf(LocalDate.now().minusMonths(4)))
            .build();
    public static LegalEntity legalEntity4 = LegalEntity.builder()
            .name("Ilia")
            .address("Lenina")
            .city("Gomel")
            .inn("4444444444")
            .createdDate(Date.valueOf(LocalDate.now().minusMonths(7)))
            .updatedDate(Date.valueOf(LocalDate.now().minusMonths(5)))
            .build();
}
