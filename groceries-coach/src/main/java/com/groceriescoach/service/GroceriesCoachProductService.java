package com.groceriescoach.service;


import com.groceriescoach.core.com.groceriescoach.core.utils.CollectionUtils;
import com.groceriescoach.core.domain.GroceriesCoachSortType;
import com.groceriescoach.core.domain.Product;
import com.groceriescoach.core.domain.ProductComparator;
import com.groceriescoach.core.domain.Store;
import com.groceriescoach.core.service.StoreSearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Service
public class GroceriesCoachProductService implements ProductSearchService {


    private final List<StoreSearchService> storeSearchServices;

    private static final Logger logger = LoggerFactory.getLogger(GroceriesCoachProductService.class);



    @Autowired
    public GroceriesCoachProductService(List<StoreSearchService> storeSearchServices) {
        this.storeSearchServices = storeSearchServices;
    }


    @Override
    public List<Product> search(String keywords, List<Store> stores, GroceriesCoachSortType sortType, boolean allSearchKeywordsRequired) throws IOException {

        List<Store> searchStores;

        if (CollectionUtils.isEmpty(stores)) {
            searchStores = Arrays.asList(Store.values()) ;
        } else {
            searchStores = stores;
        }

        List<Product> allProducts = new ArrayList<>();
        Map<Store, Future<List<Product>>> futuresMap = new HashMap<>();

/*
        storeSearchServices.stream()
                .filter(storeSearchService -> searchStores.contains(storeSearchService.getStore()))
                .forEach(storeSearchService -> futuresMap.put(storeSearchService.getStore(), storeSearchService.search(keywords, sortType)));
*/


        for (StoreSearchService storeSearchService : storeSearchServices) {
            if (searchStores.contains(storeSearchService.getStore())) {
                Future<List<Product>> listFuture = storeSearchService.search(keywords, sortType);
                futuresMap.put(storeSearchService.getStore(), listFuture);
            }
        }


        while (!futuresMap.isEmpty()) {
            List<Store> storesStillSearching = new ArrayList<>(futuresMap.keySet());
            for (Store store : storesStillSearching) {
                if (futuresMap.get(store).isDone()) {
                    try {
                        Future<List<Product>> searchResultFuture = futuresMap.remove(store);
                        allProducts.addAll(searchResultFuture.get());
                        logger.debug("{} search is complete.", store.getStoreName());
                    } catch (InterruptedException | ExecutionException e) {
                        logger.error("Unable to search " + store.getStoreName(), e);
                    }
                } else {
                    logger.debug("{} search is still in progress.", store.getStoreName());
                }
            }
            try {
                Thread.sleep(400);
            } catch (InterruptedException e) {
                logger.error("Interruption", e);
            }
        }

        if (allSearchKeywordsRequired) {
            allProducts = Product.eliminateProductsWithoutAllSearchKeywords(allProducts, keywords);
        }

        allProducts.sort(new ProductComparator(sortType));
        return allProducts;
    }

}
