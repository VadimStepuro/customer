package com.stepuro.customer.utils;

import com.stepuro.customer.model.Card;

import java.math.BigDecimal;
import java.util.regex.Pattern;

public class CardUtils {
    public static boolean validateCardOwner(Card sourceCard, Integer individualId) {
        if(sourceCard.getIndividual() == null)
            return false;

        return sourceCard.getIndividual().getIndividualId().equals(individualId);
    }

    public static boolean validateCardBalance(Card sourceCard, BigDecimal amount) {
        int result = sourceCard.getBalance().compareTo(amount);

        return result >= 0;
    }

    public static boolean isAccountNumber(String number){
        Pattern pattern = Pattern.compile("^[A-Z]{2}\\d{2}[A-Za-z\\d]{1,30}$");

        return pattern.matcher(number).matches();
    }
}
