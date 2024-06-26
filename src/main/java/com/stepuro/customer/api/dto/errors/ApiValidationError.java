package com.stepuro.customer.api.dto.errors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public class ApiValidationError implements ApiSubError{
    private String object;
    private String field;
    private Object rejectedValue;
    private String message;
}
