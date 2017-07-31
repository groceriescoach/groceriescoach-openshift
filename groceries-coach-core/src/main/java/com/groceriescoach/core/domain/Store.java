package com.groceriescoach.core.domain;


import com.groceriescoach.core.com.groceriescoach.core.utils.StringUtils;

import java.util.*;

import static com.groceriescoach.core.domain.StoreType.*;

public enum Store {

    Amcal("A", "Amcal", Pharmacies),
    //    BabiesRUs("BRU", "Babies R Us"),
    BabyAndToddlerTown("BTT", "Baby & Toddler Town", BabyShops),
    BabyBounce("BB1", "Baby Bounce", BabyShops),
    BabyBunting("BB2", "Baby Bunting", BabyShops),
    BabyKingdom("BK", "Baby Kingdom", BabyShops),
    BabySavings("BS", "Baby Savings", BabyShops),
    BabyVillage("BV", "Baby Village", BabyShops),
    BigW("BW", "Big W", Supermarkets),
    ChemistWarehouse("CW", "Chemist Warehouse", Pharmacies),
    CincottaChemist("CC", "Cincotta Chemist", Pharmacies),
    Coles("C", "Coles", Supermarkets),
    MrVitamins("M", "Mr. Vitamins", Pharmacies),
    MyChemist("MC", "My Chemist", Pharmacies),
    NursingAngel("N", "Nursing Angel", BabyShops),
    Pharmacy4Less("P4L", "Pharmacy 4 Less", Pharmacies),
    PharmacyDirect("PD", "Pharmacy Direct", Pharmacies),
    Priceline("P", "Priceline", Pharmacies),
    RoyYoung("R", "Roy Young", Pharmacies),
    Target("T", "Target", Supermarkets),
    TerryWhite("TW", "Terry White", Pharmacies),
    ThePharmacy("TP", "The Pharmacy", Pharmacies),
    Woolworths("W", "Woolworths", Supermarkets);


    private final String storeKey;
    private final String storeName;
    private final StoreType storeType;

    private static Map<StoreType, List<Store>> storeTypeToStoreMap = new HashMap<>();

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

    public boolean stringContainsStore(String stores) {
        return (StringUtils.containsIgnoreCase(stores, storeName));
    }

    public String removeStoreFromString(String stores) {
        return StringUtils.trimToEmpty(stores.replaceAll(storeName, ""));
    }

    public static List<Store> getStoresFrom(String storesStr) {
        List<Store> storeList = new ArrayList<>();
        for (Store store : Store.values()) {
            if (store.stringContainsStore(storesStr) && !storeList.contains(store)) {
                storeList.add(store);
                storesStr = store.removeStoreFromString(storesStr);
            }
        }
        return storeList;
    }

    public static List<Store> getStoresFor(StoreType storeType) {
        if (!storeTypeToStoreMap.containsKey(storeType)) {
            List<Store> storeList = new ArrayList<>();
            for (Store store : Store.values()) {
                if (store.getStoreType().equals(storeType)) {
                    storeList.add(store);
                }
            }
            storeTypeToStoreMap.put(storeType, storeList);
        }

        return storeTypeToStoreMap.get(storeType);
    }

    public static String[] getStoreKeysFor(List<Store> stores) {
        List<String> storeKeys = new ArrayList<>();
        for (Store store: stores) {
            storeKeys.add(store.getStoreKey());
        }
        return storeKeys.toArray(new String[]{});
    }

    public static Collection<Store> getAllStores() {
        return Arrays.asList(Store.values());
    }
}

