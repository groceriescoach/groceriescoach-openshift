package com.groceriescoach.core.com.groceriescoach.core.utils;

import java.text.DecimalFormat;

public class MathUtils {


    public static String formatDouble(Double d) {
        DecimalFormat df = new DecimalFormat("#.##");
        return df.format(d);
    }

    public static Double roundToTwoDecimalPlaces(Double d) {
        return Double.valueOf(formatDouble(d));
    }

}
