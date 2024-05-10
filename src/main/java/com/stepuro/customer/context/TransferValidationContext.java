package com.stepuro.customer.context;

import com.stepuro.customer.api.dto.TransferEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransferValidationContext {
    private TransferEntity transferEntity;
    private BigDecimal sourceBalance;
    private Integer sourceUserId;
    private String sourceStatus;
    private String targetStatus;
}
