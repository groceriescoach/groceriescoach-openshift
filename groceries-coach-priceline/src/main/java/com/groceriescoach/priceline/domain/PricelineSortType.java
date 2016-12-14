package com.groceriescoach.priceline.domain;

import com.groceriescoach.core.domain.GroceriesCoachSortType;

public enum PricelineSortType {
    Relevancy("relevance", "Relevancy"),
    Specials("rank", "Specials first"),
    LowestPriceFirst("123", "Lowest Price First"),
    LowestUnitPriceFirst("123", "Lowest unit price first"),
    A_Z_Brand("abc", "A-Z (Brand)"),
    A_TO_Z_ProductName("abc", "A-Z (Product Name)");


    private final String key;
    private final String value;

    PricelineSortType(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }


    public static PricelineSortType fromGroceryCoachSortType(GroceriesCoachSortType sortType) {
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
