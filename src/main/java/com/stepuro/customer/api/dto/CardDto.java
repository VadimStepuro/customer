package com.stepuro.customer.api.dto;

import com.stepuro.customer.model.enums.CardStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.*;
import org.hibernate.validator.constraints.CreditCardNumber;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Setter
@Getter
public class CardDto{
    private UUID id;

    @NotNull(message = "Credit card number can't be null")
    @CreditCardNumber(message = "Invalid credit card number")
    private String cardNumber;

    @NotNull(message = "Account number can't be null")
    @Pattern(regexp = "^[A-Z]{2}\\d{2}[A-Za-z\\d]{1,30}$", message = "Invalid account number")
    private String accountNumber;

    @NotNull(message = "Created date can't be null")
    @PastOrPresent(message = "Created date can't be in future")
    private Timestamp createdDate;

    @PastOrPresent(message = "Updated date can't be in future")
    private Timestamp updatedDate;

    @NotNull(message = "Expiry date can't be null")
    private Date expiryDate;

    @NotNull(message = "Status can't be null")
    private CardStatus status;

    @Positive
    @NotNull
    private BigDecimal balance;

    private IndividualDto individualDto;
}
