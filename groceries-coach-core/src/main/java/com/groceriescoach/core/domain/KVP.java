package com.groceriescoach.core.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class KVP {

    private String key;
    private String value;


    public KVP(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }


    public static List<KVP> fromMap(Map<String, String> map) {

        List<KVP> kvps = new ArrayList<>();
        for (Map.Entry<String, String> entry: map.entrySet()) {
            kvps.add(new KVP(entry.getKey(), entry.getValue()));
        }
        kvps.sort(new KvpComparator());
        return kvps;
    }
}
