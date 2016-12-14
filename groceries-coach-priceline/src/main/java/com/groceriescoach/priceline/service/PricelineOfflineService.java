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
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

import static com.groceriescoach.core.domain.Store.Priceline;

@Profile("offline")
@Service
public class PricelineOfflineService implements StoreSearchService {


    private static final Logger logger = LoggerFactory.getLogger(PricelineOfflineService.class);


    @Override
    public Future<List<Product>> search(String keywords, GroceriesCoachSortType sortType) {
        Document doc = null;
        try {
            String fileName = keywords.trim().replaceAll(" +", " ").replace(" ", "-");
            File pricelineHtml = new File("C:/projects/groceries-coach/groceries-coach-priceline/src/test/resources/" + fileName + ".html");
            doc = Jsoup.parse(pricelineHtml, "UTF-8");
            PricelineSearchResult searchResult = new PricelineSearchResult(doc);
            return new AsyncResult<>(searchResult.toProducts());
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
