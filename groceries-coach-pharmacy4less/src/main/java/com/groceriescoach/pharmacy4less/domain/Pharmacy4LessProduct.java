package com.groceriescoach.pharmacy4less.domain;

import com.groceriescoach.core.domain.GroceriesCoachProduct;
import com.groceriescoach.core.domain.Store;

import static com.groceriescoach.core.domain.Store.Pharmacy4Less;

public class Pharmacy4LessProduct extends GroceriesCoachProduct {

    private static final long serialVersionUID = 1760144659238592523L;

    public Store getStore() {
        return Pharmacy4Less;
    }

}

