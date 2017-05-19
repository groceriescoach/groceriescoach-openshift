package com.groceriescoach.babyvillage.service;


import com.groceriescoach.babyvillage.domain.BabyVillageProduct;
import com.groceriescoach.babyvillage.domain.BabyVillageSearchResult;
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

import static com.groceriescoach.core.domain.Store.Amcal;
import static com.groceriescoach.core.domain.Store.BabyVillage;

@Profile("online")
@Service
public class BabyVillageService implements StoreSearchService<BabyVillageProduct> {


    private static final Logger logger = LoggerFactory.getLogger(BabyVillageService.class);

    @Async
    @Override
    public Future<List<BabyVillageProduct>> search(String keywords, GroceriesCoachSortType sortType) {

        logger.debug("Searching Amcal for {}.", keywords);

        Map<String, String> requestParams = new HashMap<>();

        requestParams.put("storeId", "10301");
        requestParams.put("catalogId", "10502");
        requestParams.put("sType", "SimpleSearch");
        requestParams.put("beginIndex", "0");
        requestParams.put("pageSize", "50");

        requestParams.put("searchTerm", keywords);

        Document doc = null;
        try {

            doc = Jsoup.connect("https://www.amcal.com.au/SearchDisplay")
                    .data(requestParams)
                    .timeout(10*1000)
                    .get();

            BabyVillageSearchResult searchResult = new BabyVillageSearchResult(doc);
            List<BabyVillageProduct> products = searchResult.getProducts();

            logger.info("Found {} Amcal products for keywords[{}].", products.size(), keywords);

            return new AsyncResult<>(products);
        } catch (IOException e) {
            logger.error("Unable to search for Amcal products", e);
            return new AsyncResult<>(new ArrayList<>());
        }
    }

    @Override
    public Store getStore() {
        return BabyVillage;
    }

}
