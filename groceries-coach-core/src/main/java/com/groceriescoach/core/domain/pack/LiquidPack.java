package com.groceriescoach.core.domain.pack;

public class LiquidPack extends Pack {
    @Override
    public boolean hasUnitPrice() {
        return true;
    }

    public static LiquidPack createPackage(String productName, Double price) {
        return null;
    }
}
