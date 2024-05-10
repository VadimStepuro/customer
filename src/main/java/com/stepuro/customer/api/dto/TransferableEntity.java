package com.stepuro.customer.api.dto;

import java.math.BigDecimal;

public interface TransferableEntity {
    Integer getUserId();
    String getEntityStatus();
    BigDecimal getAmount();
}
