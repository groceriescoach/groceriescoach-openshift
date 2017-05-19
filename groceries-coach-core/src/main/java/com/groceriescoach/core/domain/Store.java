package com.groceriescoach.core.domain;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum Store {

    Amcal("A", "Amcal"),
    BabiesRUs("BRU", "Babies R Us"),
    BabyAndToddlerTown("BTT", "Baby & Toddler Town"),
    BabyBounce("BB1", "Baby Bounce"),
    BabyBunting("BB2", "Baby Bunting"),
    BabyKingdom("BK", "Baby Kingdom"),
    BabyVillage("BV", "Baby Village"),
    BigW("BW", "Big W"),
    ChemistWarehouse("CW", "Chemist Warehouse"),
    Coles("C", "Coles"),
    Kmart("K", "Kmart"),
    Priceline("P", "Priceline"),
    Target("T", "Target"),
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
