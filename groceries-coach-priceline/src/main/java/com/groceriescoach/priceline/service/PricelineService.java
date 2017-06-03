package com.groceriescoach.priceline.service;

import com.groceriescoach.core.domain.GroceriesCoachSortType;
import com.groceriescoach.core.domain.Product;
import com.groceriescoach.core.domain.Store;
import com.groceriescoach.core.service.StoreSearchService;
import com.groceriescoach.priceline.domain.PricelineSearchResult;
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

import static com.groceriescoach.core.domain.Store.Priceline;

@Profile("online")
@Service
public class PricelineService implements StoreSearchService {


    private static final Logger logger = LoggerFactory.getLogger(PricelineService.class);


    @Async
    @Override
    public Future<List<Product>> search(String keywords, GroceriesCoachSortType sortType) {

        logger.debug("Searching Priceline for {}.", keywords);

        Map<String, String> requestParams = new HashMap<>();

        requestParams.put("q", keywords);
        requestParams.put("limit", "40");

        Document doc = null;
        try {

            doc = Jsoup.connect("https://www.priceline.com.au/search")
                    .data(requestParams)
                    .timeout(5*1000)
                    .get();

            PricelineSearchResult chemistWarehouseSearchResult = new PricelineSearchResult(doc);
            List<Product> products = chemistWarehouseSearchResult.toProducts();
            logger.info("Found {} Priceline products for keywords[{}].", products.size(), keywords);
            return new AsyncResult<>(products);
        } catch (IOException e) {
            logger.error("Unable to search for Priceline products", e);
            return new AsyncResult<>(new ArrayList<>());
        }
    }

    @Override
    public Store getStore() {
        return Priceline;
    }





}
