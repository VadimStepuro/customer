package com.stepuro.customer.validation;

import com.stepuro.customer.validation.validator.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration(proxyBeanMethods = false)
public class TransferConfig {
    @Bean
    public BalanceValidator cardBalanceValidator(){
        return new BalanceValidator();
    }

    @Bean
    public OwnerValidator cardOwnerValidatior(){
        return new OwnerValidator();
    }

    @Bean
    public StatusValidator cardStatusValidator(){
        return new StatusValidator();
    }

    @Bean
    public TransferNumberValidator transferNumberValidator(){
        return new TransferNumberValidator();
    }

    @Bean
    public TransferValidationProcessor validationProcessor(List<TransferValidator> validators){
        return new TransferValidationProcessor(validators);
    }
}
