package com.groceriescoach.core.domain.pack;

import static org.apache.commons.lang3.StringUtils.containsIgnoreCase;

public class PackageCreator {

    public static Pack createPackage(String productName, Double price) {

        MultiPack multiPack = MultiPack.createPackage(productName, price);
        if (multiPack != null) {
            return multiPack;
        }

        SolidPack solidPack = SolidPack.createPackage(productName, price);
        if (solidPack != null) {
            return solidPack;
        }

        LiquidPack liquidPack = LiquidPack.createPackage(productName, price);
        if (liquidPack != null) {
            return liquidPack;
        }

        LengthPack lengthPack = LengthPack.createPackage(productName, price);
        if (lengthPack != null) {
            return lengthPack;
        }

        return new SinglePack();
    }

    public static Pack createPackage(String packageSize, String unitPriceStr, Double price) {

        if (containsIgnoreCase(unitPriceStr, "EA") || containsIgnoreCase(unitPriceStr, "SS")) {
            MultiPack multiPack = MultiPack.createPackage(packageSize, unitPriceStr, price);
            if (multiPack != null) {
                return multiPack;
            }
        }

        if (containsIgnoreCase(unitPriceStr, "kg") || containsIgnoreCase(unitPriceStr, "g") || containsIgnoreCase(unitPriceStr, "gram") || containsIgnoreCase(unitPriceStr, "kilogram")) {
            SolidPack solidPack = SolidPack.createPackage(packageSize, unitPriceStr, price);
            if (solidPack != null) {
                return solidPack;
            }
        }

        if (containsIgnoreCase(unitPriceStr, "ml") || containsIgnoreCase(unitPriceStr, "litre") || containsIgnoreCase(unitPriceStr, "liter") || containsIgnoreCase(unitPriceStr, "l")) {
            LiquidPack liquidPack = LiquidPack.createPackage(packageSize, unitPriceStr, price);
            if (liquidPack != null) {
                return liquidPack;
            }
        }

        if (containsIgnoreCase(unitPriceStr, "mm") || containsIgnoreCase(unitPriceStr, "cm") || containsIgnoreCase(unitPriceStr, "meter") || containsIgnoreCase(unitPriceStr, "metre") || containsIgnoreCase(unitPriceStr, "m")) {
            LengthPack lengthPack = LengthPack.createPackage(packageSize, unitPriceStr, price);
            if (lengthPack != null) {
                return lengthPack;
            }
        }

        return new SinglePack();
    }
}
