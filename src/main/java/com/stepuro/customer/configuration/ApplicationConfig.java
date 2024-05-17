package com.stepuro.customer.configuration;

import com.stepuro.customer.context.init.AccountTransferContextInitializer;
import com.stepuro.customer.context.init.CardTransferContextInitializer;
import com.stepuro.customer.service.AccountService;
import com.stepuro.customer.service.CardService;
import com.stepuro.customer.service.impl.TransferAmountServiceImpl;
import com.stepuro.customer.validation.TransferValidationProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class ApplicationConfig {
    @Bean
    public TransferAmountServiceImpl accountTransferAmountService(
            TransferValidationProcessor transferValidationProcessor,
            CardTransferContextInitializer cardTransferContextInitializer,
            AccountTransferContextInitializer accountTransferContextInitializer,
            CardService cardService,
            AccountService accountService
    ){
        return new TransferAmountServiceImpl(
                transferValidationProcessor,
                cardTransferContextInitializer,
                accountTransferContextInitializer,
                cardService,
                accountService
        );
    }
}
