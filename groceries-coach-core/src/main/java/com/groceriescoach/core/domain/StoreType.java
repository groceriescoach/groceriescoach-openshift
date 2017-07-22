package com.groceriescoach.core.domain;

import com.groceriescoach.core.com.groceriescoach.core.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

public enum StoreType {
    BabyShops("Baby Shops"), Pharmacies("Pharmacies"), Supermarkets("Supermarkets");

    private final String name;

    StoreType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }



    public static List<Store> getStoresFrom(String storesStr) {
        List<Store> storeList = new ArrayList<>();
        for (StoreType storeType : StoreType.values()) {
            if (storeType.stringContainsStoreType(storesStr)) {
                storeList.addAll(Store.getStoresFor(storeType));
            }
        }
        return storeList;

    }

    public boolean stringContainsStoreType(String stores) {
        return (StringUtils.containsIgnoreCase(stores, name));
    }

}
