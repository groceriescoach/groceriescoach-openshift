package com.groceriescoach.core.service;

import com.groceriescoach.core.com.groceriescoach.core.utils.StringUtils;
import com.groceriescoach.core.domain.GroceriesCoachProduct;

public abstract class AbstractStoreSearchService<P extends GroceriesCoachProduct> implements StoreSearchService {

    protected String reformatKeywordsForStore(String keywords) {
        return StringUtils.trimToEmpty(keywords).replaceAll(" +", " ").replace(" ", "+");
    }

}
