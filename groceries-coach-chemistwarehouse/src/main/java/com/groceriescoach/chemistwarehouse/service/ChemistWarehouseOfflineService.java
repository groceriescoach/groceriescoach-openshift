package com.groceriescoach.chemistwarehouse.service;


import com.groceriescoach.chemistwarehouse.domain.ChemistWarehouseSearchResult;
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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

import static com.groceriescoach.core.domain.Store.ChemistWarehouse;

@Profile("offline")
@Service
public class ChemistWarehouseOfflineService implements StoreSearchService {


    private static final Logger logger = LoggerFactory.getLogger(ChemistWarehouseOfflineService.class);


    @Async
    @Override
    public Future<List<Product>> search(String keywords, GroceriesCoachSortType sortType) {
        Document doc = null;
        try {
            String fileName = keywords.trim().replaceAll(" +", " ").replace(" ", "-");
            File chemistWarehouseHtml = new File("C:/projects/groceries-coach/groceries-coach-chemistwarehouse/src/test/resources/" + fileName + ".html");
            doc = Jsoup.parse(chemistWarehouseHtml, "UTF-8");
            ChemistWarehouseSearchResult searchResult = new ChemistWarehouseSearchResult(doc);
            return new AsyncResult<>(searchResult.toProducts());
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
