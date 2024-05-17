package com.stepuro.customer.utils;

import com.stepuro.customer.model.Account;
import com.stepuro.customer.model.enums.AccountStatus;

import java.math.BigDecimal;

public class AccountUtils {
    private AccountUtils() {}

    public static boolean validateLegalEntityOwner(Account sourceAccount, Integer legalEntityId) {
        if(sourceAccount.getLegalEntity() == null)
            return false;

        return sourceAccount.getLegalEntity().getLegalEntityId().equals(legalEntityId);
    }

    public static boolean validateAccountBalance(Account sourceAccount, BigDecimal amount) {
        int result = sourceAccount.getBalance().compareTo(amount);

        return result >= 0;
    }

    public static boolean validateStatus(Account account){
        return account.getStatus().equals(AccountStatus.ACTIVE);
    }
}
