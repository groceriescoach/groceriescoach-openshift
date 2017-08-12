package com.groceriescoach.core.service;

import com.groceriescoach.core.domain.GroceriesCoachProduct;
import com.groceriescoach.core.domain.GroceriesCoachSortType;
import com.groceriescoach.core.domain.Store;

import java.util.List;
import java.util.concurrent.Future;

public interface StoreSearchService<P extends GroceriesCoachProduct> {

    Future<List<P>> search(String keywords, GroceriesCoachSortType sortType, int page);

    Store getStore();
}
