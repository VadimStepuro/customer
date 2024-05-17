package com.stepuro.customer.service;

import com.stepuro.customer.api.dto.PaymentOrderEntityDto;

public interface SendTransferMessage {
    void transferAmount(PaymentOrderEntityDto paymentOrderEntityDto);
}
