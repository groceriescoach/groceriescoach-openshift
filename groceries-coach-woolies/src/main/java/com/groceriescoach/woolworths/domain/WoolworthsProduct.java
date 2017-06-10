package com.groceriescoach.woolworths.domain;

import com.groceriescoach.core.domain.GroceriesCoachSortType;
import com.groceriescoach.core.domain.Store;
import org.jsoup.nodes.Element;

import static com.groceriescoach.core.domain.Store.Woolworths;

public class WoolworthsProduct extends com.groceriescoach.core.domain.Product {

    @Override
    protected void extractFromProductElement(Element productElement, GroceriesCoachSortType sortType) {

    }

    @Override
    public Store getStore() {
        return Woolworths;
    }
}
