package com.groceriescoach.core.domain;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum Store {

    Coles("C", "Coles"),
    ChemistWarehouse("CW", "Chemist Warehouse"),
    Priceline("P", "Priceline"),
    Woolworths("W", "Woolworths");




    private final String storeKey;
    private final String storeName;

    private Store(String storeKey, String storeName) {
        this.storeKey = storeKey;
        this.storeName = storeName;
    }


    public String getStoreKey() {
        return storeKey;
    }

    public String getStoreName() {
        return storeName;
    }

    public static Store fromStoreKey(String storeKey) {
        for (Store store : Store.values()) {
            if (store.getStoreKey().equalsIgnoreCase(storeKey)) {
                return store;
            }
        }
        throw new IllegalArgumentException(storeKey + " is not a valid Store key.");
     }

    public static Map<String, String> getMap() {
        Map<String, String> map = new HashMap<>();
        for (Store store : Store.values()) {
            map.put(store.getStoreKey(), store.getStoreName());
        }
        return map;

    }

    public static List<Store> fromStoreKeys(String[] storeKeys) {

        List<Store> stores = new ArrayList<>();
        for (String storeKey : storeKeys) {
            stores.add(fromStoreKey(storeKey));
        }
        return stores;
    }
}
