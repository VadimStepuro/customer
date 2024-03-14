package com.stepuro.customer.repository.Samples;

import com.stepuro.customer.model.Individual;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

public class IndividualSamples {
    public static Individual individual1 = Individual.builder()
            .name("Vadim")
            .lastName("Stepuro")
            .address("Kletskova")
            .city("Grodno")
            .dayOfBirth(Date.valueOf(LocalDate.of(2003, 10, 18)))
            .createdDate(Timestamp.from(Instant.now()))
            .updatedDate(Timestamp.from(Instant.now()))
            .build();
    public static Individual individual2 = Individual.builder()
            .name("Vadim")
            .lastName("Stepuro")
            .address("Kletskova")
            .city("Grodno")
            .dayOfBirth(Date.valueOf(LocalDate.of(2003, 10, 18)))
            .createdDate(Timestamp.from(Instant.now()))
            .updatedDate(Timestamp.from(Instant.now()))
            .build();
    public static Individual individual3 = Individual.builder()
            .name("Vadim")
            .lastName("Stepuro")
            .address("Kletskova")
            .city("Grodno")
            .dayOfBirth(Date.valueOf(LocalDate.of(2003, 10, 18)))
            .createdDate(Timestamp.from(Instant.now()))
            .updatedDate(Timestamp.from(Instant.now()))
            .build();
    public static Individual individual4 = Individual.builder()
            .name("Vadim")
            .lastName("Stepuro")
            .address("Kletskova")
            .city("Grodno")
            .dayOfBirth(Date.valueOf(LocalDate.of(2003, 10, 18)))
            .createdDate(Timestamp.from(Instant.now()))
            .updatedDate(Timestamp.from(Instant.now()))
            .build();

    public static List<Individual> individualList = List.of(individual1, individual2, individual3, individual4);
}
