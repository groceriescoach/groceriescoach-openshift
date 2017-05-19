package com.groceriescoach.chemistwarehouse.service;


import com.groceriescoach.chemistwarehouse.domain.ChemistWarehouseSearchResult;
import com.groceriescoach.chemistwarehouse.domain.ChemistWarehouseSortType;
import com.groceriescoach.core.domain.GroceriesCoachSortType;
import com.groceriescoach.core.domain.Product;
import com.groceriescoach.core.domain.Store;
import com.groceriescoach.core.service.StoreSearchService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

import static com.groceriescoach.core.domain.Store.ChemistWarehouse;

@Profile("online")
@Service
public class ChemistWarehouseService implements StoreSearchService {


    private static final Logger logger = LoggerFactory.getLogger(ChemistWarehouseService.class);

    @Async
    @Override
    public Future<List<Product>> search(String keywords, GroceriesCoachSortType sortType) {

        logger.debug("Searching Chemist Warehouse for {}.", keywords);

        Map<String, String> requestParams = new HashMap<>();

        requestParams.put("sort", ChemistWarehouseSortType.Relevancy.getKey());
        requestParams.put("searchtext", keywords);
        requestParams.put("searchmode", "allwords");
        requestParams.put("size", "120");

        Document doc = null;
        try {

            doc = Jsoup.connect("http://www.chemistwarehouse.com.au/search")
                    .data(requestParams)
                    .timeout(10*1000)
                    .get();

            ChemistWarehouseSearchResult chemistWarehouseSearchResult = new ChemistWarehouseSearchResult(doc);
            List<Product> products = chemistWarehouseSearchResult.toProducts();

            logger.info("Found {} Chemist Warehouse products for keywords[{}].", products.size(), keywords);

            return new AsyncResult<>(products);
        } catch (IOException e) {
            logger.error("Unable to search for Chemist Warehouse products", e);
            return new AsyncResult<>(new ArrayList<>());
        }
    }

    @Override
    public Store getStore() {
        return ChemistWarehouse;
    }


}
