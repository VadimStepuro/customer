package com.stepuro.customer.configuration;

import com.stepuro.customer.api.dto.AccountDto;
import com.stepuro.customer.api.dto.CardDto;
import com.stepuro.customer.context.init.TransferContextInitializer;
import com.stepuro.customer.service.TransferableEntityService;
import com.stepuro.customer.service.impl.TransferAmountServiceImpl;
import com.stepuro.customer.validation.TransferValidationProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class ApplicationConfig {
    @Bean
    public TransferAmountServiceImpl<AccountDto> accountTransferAmountService(
            TransferValidationProcessor transferValidationProcessor,
            TransferContextInitializer<AccountDto> transferContextInitializer,
            TransferableEntityService<AccountDto> accountService
    ){
        return new TransferAmountServiceImpl<>(transferValidationProcessor, transferContextInitializer, accountService);
    }

    @Bean
    public TransferAmountServiceImpl<CardDto> cardTransferAmountService(
            TransferValidationProcessor transferValidationProcessor,
            TransferContextInitializer<CardDto> transferContextInitializer,
            TransferableEntityService<CardDto> accountService
    ){
        return new TransferAmountServiceImpl<>(transferValidationProcessor, transferContextInitializer, accountService);
    }
}
