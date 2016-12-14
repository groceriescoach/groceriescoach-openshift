package com.groceriescoach.core.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Comparator;

public class KvpComparator implements Comparator<KVP> {

    private GroceriesCoachSortType sortType;

    private static final Logger logger = LoggerFactory.getLogger(KvpComparator.class);

    @Override
    public int compare(KVP kvp1, KVP kvp2) {
        return kvp1.getValue().compareToIgnoreCase(kvp2.getValue());
    }
}
