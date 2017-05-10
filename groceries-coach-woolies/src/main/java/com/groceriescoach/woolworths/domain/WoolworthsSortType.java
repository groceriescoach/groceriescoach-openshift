package com.groceriescoach.woolworths.domain;

import com.groceriescoach.core.domain.GroceriesCoachSortType;

public enum WoolworthsSortType {

    Personalised("Personalised", "Personalised"),
    A_TO_Z_ProductName("Name", "A to Z (Product Name)"),
    Z_TO_A_ProductName("NameDesc", "Z to A (Product Name"),
    Aisle("Aisle", "Aisle"),
    New("AvailableDate", "New"),
    Special("Special", "Special"),
    PriceLowToHigh("PriceAsc", "Price Low to High"),
    PriceHighToLow("PriceDesc", "Price High to Low"),
    UnitPriceLowToHigh("CUPAsc", "Unit Price Low to High"),
    UnitPriceHighToLow("CUPDesc", "Unit Price High to Low");

    private final String key;
    private final String value;

    WoolworthsSortType(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }


    public static WoolworthsSortType fromGroceriesCoachSortType(GroceriesCoachSortType sortType) {
        switch (sortType) {
/*
            case Relevance:
                return Relevance;
*/
            case A_TO_Z_ProductName:
                return A_TO_Z_ProductName;
/*
            case Special:
                return Special;
*/
            case PriceLowToHigh:
                return PriceLowToHigh;
            case UnitPriceLowToHigh:
                return UnitPriceLowToHigh;
            default:
                throw new IllegalArgumentException("Invalid GroceriesCoachSortType: " + sortType);
        }
    }
}
