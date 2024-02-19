package com.stepuro.customer.api.dto;

import com.stepuro.customer.model.enums.CardStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.validator.constraints.CreditCardNumber;

import java.util.Date;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Setter
@Getter
public class CardDto {
    private UUID id;

    @NotNull(message = "Credit card number can't be null")
    @CreditCardNumber(message = "Invalid credit card number")
    private String cardNumber;

    @NotNull(message = "Account number can't be null")
    @Pattern(regexp = "^[A-Z]{2}\\d{2}[A-Za-z\\d]{1,30}$", message = "Invalid account number")
    private String accountNumber;

    @NotNull(message = "Created date can't be null")
    @PastOrPresent(message = "Created date can't be in future")
    private Date createdDate;

    @PastOrPresent(message = "Updated date can't be in future")
    private Date updatedDate;

    @NotNull(message = "Expiry date can't be null")
    private java.sql.Date expiryDate;

    @NotNull(message = "Status can't be null")
    private CardStatus status;

    private IndividualDto individualDto;
}
