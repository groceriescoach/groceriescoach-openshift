package com.groceriescoach.babiesrus.service;


import com.groceriescoach.babiesrus.domain.BabiesRUsProduct;
import com.groceriescoach.babiesrus.domain.BabiesRUsSearchResult;
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

import static com.groceriescoach.core.domain.Store.BabiesRUs;

@Profile("online")
@Service
public class BabiesRUsService implements StoreSearchService<BabiesRUsProduct> {


    private static final Logger logger = LoggerFactory.getLogger(BabiesRUsService.class);

    @Async
    @Override
    public Future<List<BabiesRUsProduct>> search(String keywords, GroceriesCoachSortType sortType) {

        logger.debug("Searching Babies R Us for {}.", keywords);

        Map<String, String> requestParams = new HashMap<>();


        https://www.toysrus.com.au/search-results/?q=wipes#P9840508=1|100


        requestParams.put("q", keywords + "#P9840508=1|100");

        Document doc = null;
        try {

            doc = Jsoup.connect("https://www.toysrus.com.au/search-results")
                    .data(requestParams)
                    .timeout(10*1000)
                    .get();

            BabiesRUsSearchResult searchResult = new BabiesRUsSearchResult(doc);
            List<BabiesRUsProduct> products = searchResult.getProducts();

            logger.info("Found {} Babies R Us products for keywords[{}].", products.size(), keywords);

            return new AsyncResult<>(products);
        } catch (IOException e) {
            logger.error("Unable to search for Babies R Us products", e);
            return new AsyncResult<>(new ArrayList<>());
        }
    }

    @Override
    public Store getStore() {
        return BabiesRUs;
    }
}
