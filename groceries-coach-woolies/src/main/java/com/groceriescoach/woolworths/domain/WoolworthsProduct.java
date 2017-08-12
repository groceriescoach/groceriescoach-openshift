package com.groceriescoach.woolworths.domain;

import com.groceriescoach.core.domain.GroceriesCoachProduct;
import com.groceriescoach.core.domain.Store;

import static com.groceriescoach.core.domain.Store.Woolworths;

public class WoolworthsProduct extends GroceriesCoachProduct {

    private static final long serialVersionUID = 1441548278080225404L;

    @Override
    public Store getStore() {
        return Woolworths;
    }
}
