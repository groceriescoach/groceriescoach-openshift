package com.groceriescoach.core.domain.pack;

public class LengthPack extends Pack {
    @Override
    boolean hasUnitPrice() {
        return true;
    }

    public static LengthPack createPackage(String productName, Double price) {
        return null;
    }
}
