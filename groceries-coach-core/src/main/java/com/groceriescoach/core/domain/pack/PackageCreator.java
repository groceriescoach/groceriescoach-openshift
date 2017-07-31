package com.groceriescoach.core.domain.pack;

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
}
