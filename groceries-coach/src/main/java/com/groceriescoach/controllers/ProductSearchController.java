package com.groceriescoach.controllers;


import com.groceriescoach.core.domain.GroceriesCoachSortType;
import com.groceriescoach.core.domain.KVP;
import com.groceriescoach.core.domain.Product;
import com.groceriescoach.core.domain.Store;
import com.groceriescoach.service.ProductSearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.groceriescoach.controllers.ApiUrls.ProductSearch.*;

@RestController
public class ProductSearchController {

    private final ProductSearchService productSearchService;

    @Autowired
    public ProductSearchController(ProductSearchService productSearchService) {
        this.productSearchService = productSearchService;
    }

    private static final Logger logger = LoggerFactory.getLogger(ProductSearchController.class);


    @RequestMapping(value = SEARCH_URL)
    public ResponseEntity<List<Product>> search(
            @RequestParam(value = KEYWORDS) String keywords,
            @RequestParam(value = STORES, required = false) String[] storeKeys,
            @RequestParam(value = SORT_BY, required = false) String sortBy,
            @RequestParam(value = ALL_SEARCH_KEYWORDS_REQUIRED, required = false) boolean allSearchKeywordsRequired)
            throws IOException {

        logger.info("Received search request: keywords = [{}], stores = [{}], sortBy = [{}], allSearchKeywordsRequired = [{}].",
                keywords, storeKeys, sortBy, allSearchKeywordsRequired);

        GroceriesCoachSortType sortType = GroceriesCoachSortType.fromKey(sortBy);
        List<Store> stores = new ArrayList<>();
        if (storeKeys != null && storeKeys.length > 0) {
            stores.addAll(Store.fromStoreKeys(storeKeys));
        }

        List<Product> products = productSearchService.search(keywords, stores, sortType, allSearchKeywordsRequired);
        logger.info("Returning {} results.", products.size());
        return ResponseEntity.ok(products);
    }


    @RequestMapping(value = SORT_TYPES_URL)
    public ResponseEntity<List<KVP>> getSortTypes() {
        return ResponseEntity.ok(KVP.fromMap(GroceriesCoachSortType.getMap()));
    }


    @RequestMapping(value = STORES_URL)
    public ResponseEntity<List<KVP>> getStores() {
        return ResponseEntity.ok(KVP.fromMap(Store.getMap()));
    }

}
