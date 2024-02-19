package com.stepuro.customer.api.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.PastOrPresent;
import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class IndividualDto {
    private Integer individualId;

    @NotNull(message = "Name can't be null")
    private String name;

    private String lastName;

    @NotNull(message = "Day of birth can't be null")
    @Past(message = "Day of birth can't be in future")
    private java.sql.Date dayOfBirth;

    @NotNull(message = "Created date can't be null")
    @PastOrPresent(message = "Created date can't be in future")
    private Date createdDate;

    @PastOrPresent(message = "Updated date can't be in future")
    private Date updatedDate;

    @NotNull(message = "Address can't be null")
    private String address;

    @NotNull(message = "City can't be null")
    private String city;
}
