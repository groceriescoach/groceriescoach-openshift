package com.groceriescoach.core.com.groceriescoach.core.utils;

import java.text.DecimalFormat;

import static com.groceriescoach.core.com.groceriescoach.core.utils.StringUtils.removeCurrencySymbols;
import static com.groceriescoach.core.com.groceriescoach.core.utils.StringUtils.removeThousandSeparators;

public class CurrencyUtils {

    private static DecimalFormat df = new DecimalFormat("0.00");

    public static String formatCurrencyAmount(Double amountInCents) {
        if (amountInCents >= 100) {
            return "$" + df.format(amountInCents/100);
        } else {
            return df.format(amountInCents) + " cents";
        }
    }

    public static Double extractPriceFrom(String priceStr, Double priceIfNotFound) {
        if (StringUtils.isNotBlank(priceStr)) {
            return Double.parseDouble(removeThousandSeparators(removeCurrencySymbols(priceStr)));
        } else {
            return priceIfNotFound;
        }
    }

}
