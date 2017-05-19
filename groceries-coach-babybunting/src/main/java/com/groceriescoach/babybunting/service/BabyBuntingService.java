package com.groceriescoach.babybunting.service;


import com.groceriescoach.babybunting.domain.BabyBuntingProduct;
import com.groceriescoach.babybunting.domain.BabyBuntingSearchResult;
import com.groceriescoach.core.domain.GroceriesCoachSortType;
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

import static com.groceriescoach.core.domain.Store.BabyBunting;

@Profile("online")
@Service
public class BabyBuntingService implements StoreSearchService<BabyBuntingProduct> {


    private static final Logger logger = LoggerFactory.getLogger(BabyBuntingService.class);

    @Async
    @Override
    public Future<List<BabyBuntingProduct>> search(String keywords, GroceriesCoachSortType sortType) {

        logger.debug("Searching Baby Bunting for {}.", keywords);

        Map<String, String> requestParams = new HashMap<>();

//        requestParams.put("limit", "120");
//        requestParams.put("order", "relevance");
        requestParams.put("q", keywords);

        Document doc = null;
        try {
            doc = Jsoup.connect("https://www.babybunting.com.au/catalogsearch/result/index")
                    .data(requestParams)
                    .timeout(10*1000)
                    .get();

            BabyBuntingSearchResult searchResult = new BabyBuntingSearchResult(doc);

            List<BabyBuntingProduct> products = searchResult.getProducts();

            logger.info("Found {} Baby Bunting products for keywords[{}].", products.size(), keywords);

            return new AsyncResult<>(products);
        } catch (IOException e) {
            logger.error("Unable to search for Baby Bunting products", e);
            return new AsyncResult<>(new ArrayList<>());
        }
    }

    @Override
    public Store getStore() {
        return BabyBunting;
    }

}
