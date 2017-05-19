package com.groceriescoach.babybounce.service;

import com.groceriescoach.babybounce.domain.BabyBounceProduct;
import com.groceriescoach.babybounce.domain.BabyBounceSearchResult;
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

import static com.groceriescoach.core.domain.Store.BabyBounce;

@Profile("online")
@Service
public class BabyBounceService implements StoreSearchService<BabyBounceProduct> {


    private static final Logger logger = LoggerFactory.getLogger(BabyBounceService.class);

    @Async
    @Override
    public Future<List<BabyBounceProduct>> search(String keywords, GroceriesCoachSortType sortType) {

        logger.debug("Searching Baby Bounce for {}.", keywords);

        Map<String, String> requestParams = new HashMap<>();

        requestParams.put("limit", "all");
        requestParams.put("q", keywords);

        Document doc = null;
        try {

//            https://babybounce.com.au/catalogsearch/result/?q=bottle&limit=all

            doc = Jsoup.connect("https://babybounce.com.au/catalogsearch/result")
                    .data(requestParams)
                    .timeout(10*1000)
                    .get();

            BabyBounceSearchResult searchResult = new BabyBounceSearchResult(doc);
            List<BabyBounceProduct> products = searchResult.getProducts();

            logger.info("Found {} Baby Bounce products for keywords[{}].", products.size(), keywords);

            return new AsyncResult<>(products);
        } catch (IOException e) {
            logger.error("Unable to search for Baby Bounce products", e);
            return new AsyncResult<>(new ArrayList<>());
        }
    }

    @Override
    public Store getStore() {
        return BabyBounce;
    }


}
