package com.stepuro.customer.service.impl;

import com.stepuro.customer.api.dto.PaymentOrderEntityDto;
import com.stepuro.customer.service.SendTransferMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class SendTransferMessageServiceImpl implements SendTransferMessage {
    private final KafkaTemplate<String, PaymentOrderEntityDto> kafkaTemplate;

    @Value(value = "${kafka.topic-name.transfer-amount}")
    private String transferAmountTopicName;

    public SendTransferMessageServiceImpl(KafkaTemplate<String, PaymentOrderEntityDto> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void transferAmount(PaymentOrderEntityDto paymentOrderEntityDto) {
        kafkaTemplate.send(transferAmountTopicName, paymentOrderEntityDto.getUserId().toString(), paymentOrderEntityDto);
    }
}
