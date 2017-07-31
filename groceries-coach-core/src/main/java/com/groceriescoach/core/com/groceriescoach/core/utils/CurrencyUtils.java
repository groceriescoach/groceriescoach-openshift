package com.groceriescoach.core.com.groceriescoach.core.utils;

import java.text.DecimalFormat;

public class CurrencyUtils {

    private static DecimalFormat df = new DecimalFormat("0.00");

    public static String formatCurrencyAmount(Double amount) {
        if (amount >= 1) {
            return "$" + df.format(amount);
        } else {
            Double cents = amount * 100;
            return df.format(cents) + " cents";
        }
    }

}
