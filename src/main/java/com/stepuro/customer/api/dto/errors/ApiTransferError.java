package com.stepuro.customer.api.dto.errors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public class ApiTransferError implements ApiSubError{
    private String message;
    private String exceptionName;
}
