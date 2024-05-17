package com.stepuro.customer.context;

import com.stepuro.customer.context.init.AccountTransferContextInitializer;
import com.stepuro.customer.context.init.CardTransferContextInitializer;
import com.stepuro.customer.service.AccountService;
import com.stepuro.customer.service.CardService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class ContextConfig {

    @Bean
    public CardTransferContextInitializer cardTransferContextInitializer(final CardService cardService) {
        return new CardTransferContextInitializer(cardService);
    }

    @Bean
    public AccountTransferContextInitializer accountTransferContextInitializer(final AccountService accountService) {
        return new AccountTransferContextInitializer(accountService);
    }
}
