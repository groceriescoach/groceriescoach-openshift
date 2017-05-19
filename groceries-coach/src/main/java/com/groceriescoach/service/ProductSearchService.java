package com.groceriescoach.service;

import com.groceriescoach.core.domain.GroceriesCoachSortType;
import com.groceriescoach.core.domain.Product;
import com.groceriescoach.core.domain.Store;

import java.io.IOException;
import java.util.List;

public interface ProductSearchService {

    List<Product> search(String keyword, List<Store> stores, GroceriesCoachSortType sortType, boolean allSearchKeywordsRequired)
            throws IOException;
}
