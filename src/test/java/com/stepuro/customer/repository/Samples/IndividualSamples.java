package com.stepuro.customer.repository.Samples;

import com.stepuro.customer.model.Individual;

import java.sql.Date;
import java.time.LocalDate;

public class IndividualSamples {
    public static Individual individual1 = Individual.builder()
            .name("Vadim")
            .lastName("Stepuro")
            .address("Kletskova")
            .city("Grodno")
            .dayOfBirth(Date.valueOf(LocalDate.of(2003, 10, 18)))
            .createdDate(Date.valueOf(LocalDate.now()))
            .updatedDate(Date.valueOf(LocalDate.now()))
            .build();
    public static Individual individual2 = Individual.builder()
            .name("Vadim")
            .lastName("Stepuro")
            .address("Kletskova")
            .city("Grodno")
            .dayOfBirth(Date.valueOf(LocalDate.of(2003, 10, 18)))
            .createdDate(Date.valueOf(LocalDate.now()))
            .updatedDate(Date.valueOf(LocalDate.now()))
            .build();
    public static Individual individual3 = Individual.builder()
            .name("Vadim")
            .lastName("Stepuro")
            .address("Kletskova")
            .city("Grodno")
            .dayOfBirth(Date.valueOf(LocalDate.of(2003, 10, 18)))
            .createdDate(Date.valueOf(LocalDate.now()))
            .updatedDate(Date.valueOf(LocalDate.now()))
            .build();
    public static Individual individual4 = Individual.builder()
            .name("Vadim")
            .lastName("Stepuro")
            .address("Kletskova")
            .city("Grodno")
            .dayOfBirth(Date.valueOf(LocalDate.of(2003, 10, 18)))
            .createdDate(Date.valueOf(LocalDate.now()))
            .updatedDate(Date.valueOf(LocalDate.now()))
            .build();
}
