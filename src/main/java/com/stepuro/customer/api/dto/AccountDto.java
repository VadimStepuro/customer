package com.stepuro.customer.api.dto;

import com.stepuro.customer.model.status.AccountStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class AccountDto {
    private UUID id;

    @NotNull(message = "Account number can't be null")
    @Pattern(regexp = "^[A-Z]{2}\\d{2}[A-Za-z\\d]{1,30}$", message = "Invalid IBAN number")
    private String accountNumber;

    @NotNull(message = "Created date can't be null")
    @PastOrPresent(message = "Created date can't be in future")
    private Date createdDate;

    @PastOrPresent(message = "Updated date can't be in future")
    private Date updatedDate;

    @NotNull(message = "Status can't be null")
    private AccountStatus status;

    private LegalEntityDto legalEntityDto;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public AccountStatus getStatus() {
        return status;
    }

    public void setStatus(AccountStatus status) {
        this.status = status;
    }

    public LegalEntityDto getLegalEntityDto() {
        return legalEntityDto;
    }

    public void setLegalEntityDto(LegalEntityDto legalEntityDto) {
        this.legalEntityDto = legalEntityDto;
    }
}
