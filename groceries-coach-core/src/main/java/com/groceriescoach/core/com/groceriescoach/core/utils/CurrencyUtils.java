package com.groceriescoach.core.com.groceriescoach.core.utils;

import java.text.DecimalFormat;

public class CurrencyUtils {

    private static DecimalFormat df = new DecimalFormat("0.00");

    public static String formatCurrencyAmount(Double amountInCents) {
        if (amountInCents >= 100) {
            return "$" + df.format(amountInCents/100);
        } else {
            return df.format(amountInCents) + " cents";
        }
    }

}
