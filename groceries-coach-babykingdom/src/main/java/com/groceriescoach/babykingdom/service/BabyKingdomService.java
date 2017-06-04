package com.groceriescoach.babykingdom.service;


import com.groceriescoach.babykingdom.domain.BabyKingdomProduct;
import com.groceriescoach.babykingdom.domain.BabyKingdomSearchResult;
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

import static com.groceriescoach.core.domain.Store.BabyKingdom;

@Profile("online")
@Service
public class BabyKingdomService implements StoreSearchService {


    private static final Logger logger = LoggerFactory.getLogger(BabyKingdomService.class);

    @Async
    @Override
    public Future<List<BabyKingdomProduct>> search(String keywords, GroceriesCoachSortType sortType) {

        logger.debug("Searching Baby Kingdom for {}.", keywords);

        Map<String, String> requestParams = new HashMap<>();
        requestParams.put("b1", "search");
        requestParams.put("keyword", keywords);

        Document doc = null;
        try {

            doc = Jsoup.connect("https://www.babykingdom.com.au/shopsearch.asp?search=Yes")
                    .data(requestParams)
                    .timeout(10*1000)
                    .maxBodySize(0)
                    .post();

            BabyKingdomSearchResult searchResult = new BabyKingdomSearchResult(doc);
            List<BabyKingdomProduct> products = searchResult.getProducts();

            logger.info("Found {} Baby Kingdom products for keywords[{}].", products.size(), keywords);

            return new AsyncResult<>(products);
        } catch (IOException e) {
            logger.error("Unable to search for Baby Kingdom products", e);
            return new AsyncResult<>(new ArrayList<>());
        }
    }

    @Override
    public Store getStore() {
        return BabyKingdom;
    }


}
