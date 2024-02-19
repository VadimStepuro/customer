package com.stepuro.customer.api.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class LegalEntityDto {
    private Integer legalEntityId;

    @NotNull(message = "Name can't be null")
    private String name;

    @NotNull(message = "Created date can't be null")
    @PastOrPresent(message = "Created date can't be in future")
    private Date createdDate;

    @PastOrPresent(message = "Updated date can't be in future")
    private Date updatedDate;

    @NotNull(message = "Address can't be null")
    private String address;

    @NotNull(message = "City can't be null")
    private String city;

    @NotNull(message = "Inn can't be null")
    @Pattern(regexp = "^[\\d+]{10,12}$", message = "Invalid inn. It can contain only digits with length 10-12")
    private String inn;
}
