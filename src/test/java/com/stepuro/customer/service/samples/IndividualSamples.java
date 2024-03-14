package com.stepuro.customer.service.samples;

import com.stepuro.customer.api.dto.IndividualDto;
import com.stepuro.customer.api.dto.mapper.IndividualMapper;
import com.stepuro.customer.model.Individual;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class IndividualSamples {
    public static Individual individual1 = Individual.builder()
            .individualId(1)
            .name("Vadim")
            .lastName("Stepuro")
            .address("Kletskova")
            .city("Grodno")
            .dayOfBirth(Date.valueOf(LocalDate.of(2003, 10, 18)))
            .createdDate(Timestamp.valueOf(LocalDateTime.now()))
            .updatedDate(Timestamp.valueOf(LocalDateTime.now()))
            .build();
    public static Individual individual2 = Individual.builder()
            .individualId(2)
            .name("Vadim")
            .lastName("Stepuro")
            .address("Kletskova")
            .city("Grodno")
            .dayOfBirth(Date.valueOf(LocalDate.of(2003, 10, 18)))
            .createdDate(Timestamp.valueOf(LocalDateTime.now()))
            .updatedDate(Timestamp.valueOf(LocalDateTime.now()))
            .build();
    public static Individual individual3 = Individual.builder()
            .individualId(3)
            .name("Vadim")
            .lastName("Stepuro")
            .address("Kletskova")
            .city("Grodno")
            .dayOfBirth(Date.valueOf(LocalDate.of(2003, 10, 18)))
            .createdDate(Timestamp.valueOf(LocalDateTime.now()))
            .updatedDate(Timestamp.valueOf(LocalDateTime.now()))
            .build();
    public static Individual individual4 = Individual.builder()
            .individualId(4)
            .name("Vadim")
            .lastName("Stepuro")
            .address("Kletskova")
            .city("Grodno")
            .dayOfBirth(Date.valueOf(LocalDate.of(2003, 10, 18)))
            .createdDate(Timestamp.valueOf(LocalDateTime.now()))
            .updatedDate(Timestamp.valueOf(LocalDateTime.now()))
            .build();

    public static List<Individual> individualList = List.of(individual1, individual2, individual3, individual4);

    public static IndividualDto individualDto = IndividualMapper.INSTANCE.individualToIndividualDto(individual1);
}
