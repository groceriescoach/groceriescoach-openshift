package com.groceriescoach.mrvitamins.domain;

import com.groceriescoach.core.domain.GroceriesCoachProduct;
import com.groceriescoach.core.domain.Store;

import static com.groceriescoach.core.domain.Store.MrVitamins;

public class MrVitaminsProduct extends GroceriesCoachProduct {


    private static final long serialVersionUID = -8331289327652739035L;

    @Override
    public Store getStore() {
        return MrVitamins;
    }

}
