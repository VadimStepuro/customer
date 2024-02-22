package com.stepuro.customer.api.dto;

import com.stepuro.customer.model.enums.AccountStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Setter
@Getter
public class AccountDto {
    private UUID id;

    @NotNull(message = "Account number can't be null")
    @Pattern(regexp = "^[A-Z]{2}\\d{2}[A-Za-z\\d]{1,30}$", message = "Invalid IBAN number")
    private String accountNumber;

    @NotNull(message = "Created date can't be null")
    @PastOrPresent(message = "Created date can't be in future")
    private Timestamp createdDate;

    @PastOrPresent(message = "Updated date can't be in future")
    private Timestamp updatedDate;

    @NotNull(message = "Status can't be null")
    private AccountStatus status;

    @Positive
    @NotNull
    private BigDecimal balance;

    private LegalEntityDto legalEntityDto;
}
