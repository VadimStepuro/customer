package com.stepuro.customer.utils;

import com.stepuro.customer.model.Account;

import java.math.BigDecimal;

public class AccountUtils {
    public static boolean validateLegalEntityOwner(Account sourceAccount, Integer legalEntityId) {
        if(sourceAccount.getLegalEntity() == null)
            return false;

        return sourceAccount.getLegalEntity().getLegalEntityId().equals(legalEntityId);
    }

    public static boolean validateAccountBalance(Account sourceAccount, BigDecimal amount) {
        int result = sourceAccount.getBalance().compareTo(amount);

        return result >= 0;
    }
}
