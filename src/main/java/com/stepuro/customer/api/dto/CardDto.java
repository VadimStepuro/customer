package com.stepuro.customer.api.dto;

import com.stepuro.customer.model.status.CardStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.validator.constraints.CreditCardNumber;

import java.util.Date;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
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

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date created_date) {
        this.createdDate = created_date;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public java.sql.Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(java.sql.Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public CardStatus getStatus() {
        return status;
    }

    public void setStatus(CardStatus status) {
        this.status = status;
    }

    public IndividualDto getIndividualDto() {
        return individualDto;
    }

    public void setIndividualDto(IndividualDto individualDto) {
        this.individualDto = individualDto;
    }
}
