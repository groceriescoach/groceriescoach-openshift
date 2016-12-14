package com.groceriescoach.core.service;

import com.groceriescoach.core.domain.GroceriesCoachSortType;
import com.groceriescoach.core.domain.Product;
import com.groceriescoach.core.domain.Store;

import java.util.List;
import java.util.concurrent.Future;

public interface StoreSearchService {

    Future<List<Product>> search(String keywords, GroceriesCoachSortType sortType);

    Store getStore();
}
