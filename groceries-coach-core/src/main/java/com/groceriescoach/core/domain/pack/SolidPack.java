package com.groceriescoach.core.domain.pack;

public class SolidPack extends Pack {
    @Override
    public boolean hasUnitPrice() {
        return true;
    }

    public static SolidPack createPackage(String productName, Double price) {
        return null;
    }
}
