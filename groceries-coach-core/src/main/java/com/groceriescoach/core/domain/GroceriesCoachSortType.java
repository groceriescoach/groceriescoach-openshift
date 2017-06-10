package com.groceriescoach.core.domain;

import java.util.HashMap;
import java.util.Map;

public enum GroceriesCoachSortType {
    ProductName("Name", "Product Name"),
    Price("Price", "Price"),
    UnitPrice("UnitPrice", "Unit Price") {
        @Override
        protected boolean isUnitPriceRequired() {
            return true;
        }
    };

    private final String key;
    private final String value;

    GroceriesCoachSortType(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public static Map<String, String> getMap() {
        Map<String, String> map = new HashMap<>();
        for (GroceriesCoachSortType sortType : GroceriesCoachSortType.values()) {
            map.put(sortType.getKey(), sortType.getValue());
        }
        return map;
    }

    public static GroceriesCoachSortType fromKey(String key) {
        for (GroceriesCoachSortType sortType : GroceriesCoachSortType.values()) {
            if (sortType.getKey().equals(key)) {
                return sortType;
            }
        }
        throw new IllegalArgumentException(key + " is not a valid GroceryCoachSortType key");
    }

    protected boolean isUnitPriceRequired() {
        return false;
    }

}
