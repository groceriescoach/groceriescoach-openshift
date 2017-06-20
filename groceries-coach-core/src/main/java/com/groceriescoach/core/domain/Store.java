package com.groceriescoach.core.domain;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static com.groceriescoach.core.domain.StoreType.*;

public enum Store {

    Amcal("A", "Amcal", Pharmacy),
//    BabiesRUs("BRU", "Babies R Us"),
    BabyAndToddlerTown("BTT", "Baby & Toddler Town", BabyShop),
    BabyBounce("BB1", "Baby Bounce", BabyShop),
    BabyBunting("BB2", "Baby Bunting", BabyShop),
    BabyKingdom("BK", "Baby Kingdom", BabyShop),
    BabyVillage("BV", "Baby Village", BabyShop),
    BigW("BW", "Big W", Supermarket),
    ChemistWarehouse("CW", "Chemist Warehouse", Pharmacy),
    CincottaChemist("CC", "Cincotta Chemist", Pharmacy),
    Coles("C", "Coles", Supermarket),
    MrVitamins("M", "Mr. Vitamins", Pharmacy),
    NursingAngel("N", "Nursing Angel", Pharmacy),
    Pharmacy4Less("P4L", "Pharmacy 4 Less", Pharmacy),
    Priceline("P", "Priceline", Pharmacy),
    Target("T", "Target", Supermarket),
    Woolworths("W", "Woolworths", Supermarket);


    private final String storeKey;
    private final String storeName;
    private final StoreType storeType;

    Store(String storeKey, String storeName, StoreType storeType) {
        this.storeKey = storeKey;
        this.storeName = storeName;
        this.storeType = storeType;
    }

    public String getStoreKey() {
        return storeKey;
    }

    public String getStoreName() {
        return storeName;
    }

    public StoreType getStoreType() {
        return storeType;
    }

    public static Store fromStoreKey(String storeKey) {
        for (Store store : Store.values()) {
            if (store.getStoreKey().equalsIgnoreCase(storeKey)) {
                return store;
            }
        }
        throw new IllegalArgumentException(storeKey + " is not a valid Store key.");
     }


    public static Map<String, Map<String, String>> getMap() {
        Map<String, Map<String, String>> storeTypeToStoresMap = new TreeMap<>();
        for (Store store : Store.values()) {
            final StoreType storeType = store.getStoreType();
            if (!storeTypeToStoresMap.containsKey(storeType.getName())) {
                storeTypeToStoresMap.put(storeType.getName(), new TreeMap<>());
            }
            final Map<String, String> storeTypeMap = storeTypeToStoresMap.get(storeType.getName());
            storeTypeMap.put(store.getStoreKey(), store.getStoreName());
        }
        return storeTypeToStoresMap;
    }

    public static List<Store> fromStoreKeys(String[] storeKeys) {

        List<Store> stores = new ArrayList<>();
        for (String storeKey : storeKeys) {
            stores.add(fromStoreKey(storeKey));
        }
        return stores;
    }
}

enum StoreType {
    BabyShop("Baby Shops"), Pharmacy("Pharmacies"), Supermarket("Supermarkets");

    private final String name;

    StoreType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
