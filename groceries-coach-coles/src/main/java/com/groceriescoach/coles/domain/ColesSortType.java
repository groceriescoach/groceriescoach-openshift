package com.groceriescoach.coles.domain;

import com.groceriescoach.core.domain.GroceriesCoachSortType;

public enum ColesSortType {
    Relevancy("", "Relevancy"),
    Specials("10601_5", "Specials first"),
    LowestPriceFirst("10601_3", "Lowest Price First"),
    LowestUnitPriceFirst("10601_4", "Lowest unit price first"),
    A_Z_Brand("1", "A-Z (Brand)"),
    A_TO_Z_ProductName("2", "A-Z (Product Name)");


    private final String key;
    private final String value;

    ColesSortType(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }


    public static ColesSortType fromGroceryCoachSortType(GroceriesCoachSortType sortType) {
        switch (sortType) {
            case A_TO_Z_ProductName:
                return A_TO_Z_ProductName;
/*
            case Relevance:
                return Relevancy;
            case Special:
                return Specials;
*/
            case PriceLowToHigh:
                return LowestPriceFirst;
            case UnitPriceLowToHigh:
                return LowestUnitPriceFirst;
            default:
                throw new IllegalArgumentException("Unknown sorttype: " + sortType);
        }
    }
}
