package com.groceriescoach.woolworths.domain;

import com.groceriescoach.core.domain.GroceriesCoachProduct;
import com.groceriescoach.core.domain.Store;

import static com.groceriescoach.core.domain.Store.Woolworths;

public class WoolworthsProduct extends GroceriesCoachProduct {

    @Override
    public Store getStore() {
        return Woolworths;
    }
}
