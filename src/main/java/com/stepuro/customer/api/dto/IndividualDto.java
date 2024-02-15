package com.stepuro.customer.api.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
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

    public Integer getIndividualId() {
        return individualId;
    }

    public void setIndividualId(Integer individualId) {
        this.individualId = individualId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public java.sql.Date getDayOfBirth() {
        return dayOfBirth;
    }

    public void setDayOfBirth(java.sql.Date dayOfBirth) {
        this.dayOfBirth = dayOfBirth;
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
}
