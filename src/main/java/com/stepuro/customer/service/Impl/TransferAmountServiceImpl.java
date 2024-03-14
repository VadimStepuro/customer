package com.stepuro.customer.service.Impl;

import com.stepuro.customer.api.dto.PaymentOrderEntityDto;
import com.stepuro.customer.service.TransferAmountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class TransferAmountServiceImpl implements TransferAmountService {
    @Autowired
    private KafkaTemplate<String, PaymentOrderEntityDto> kafkaTemplate;
    @Value(value = "${kafka.topic-name.transfer-amount}")
    private String transferAmountTopicName;
    @Override
    public void transferAmount(PaymentOrderEntityDto paymentOrderEntityDto) {
        kafkaTemplate.send(transferAmountTopicName, paymentOrderEntityDto.getUserId().toString(), paymentOrderEntityDto);
    }
}
