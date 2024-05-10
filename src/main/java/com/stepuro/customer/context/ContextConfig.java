package com.stepuro.customer.context;

import com.stepuro.customer.api.dto.AccountDto;
import com.stepuro.customer.api.dto.CardDto;
import com.stepuro.customer.context.init.TransferContextInitializer;
import com.stepuro.customer.service.TransferableEntityService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class ContextConfig {

    @Bean
    public TransferContextInitializer<CardDto> cardTransferContextInitializer(final TransferableEntityService<CardDto> cardService) {
        return new TransferContextInitializer<>(cardService);
    }

    @Bean
    public TransferContextInitializer<AccountDto> accountTransferContextInitializer(final TransferableEntityService<AccountDto> accountService) {
        return new TransferContextInitializer<>(accountService);
    }
}
