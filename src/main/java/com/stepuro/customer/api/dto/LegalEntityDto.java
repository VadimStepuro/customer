package com.stepuro.customer.api.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
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
    private int inn;

    public Integer getLegalEntityId() {
        return legalEntityId;
    }

    public void setLegalEntityId(Integer legalEntityId) {
        this.legalEntityId = legalEntityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getInn() {
        return inn;
    }

    public void setInn(int inn) {
        this.inn = inn;
    }
}
