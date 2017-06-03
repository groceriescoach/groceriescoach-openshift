package com.groceriescoach.target.service;


import com.groceriescoach.core.domain.GroceriesCoachSortType;
import com.groceriescoach.core.domain.Store;
import com.groceriescoach.core.service.StoreSearchService;
import com.groceriescoach.target.domain.TargetProduct;
import com.groceriescoach.target.domain.TargetSearchResult;
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

import static com.groceriescoach.core.domain.Store.Target;

@Profile("online")
@Service
public class TargetService implements StoreSearchService<TargetProduct> {


    private static final Logger logger = LoggerFactory.getLogger(TargetService.class);

    @Async
    @Override
    public Future<List<TargetProduct>> search(String keywords, GroceriesCoachSortType sortType) {

        logger.debug("Searching Target for {}.", keywords);

        Map<String, String> requestParams = new HashMap<>();

        requestParams.put("Nrpp", "90");

        requestParams.put("text", keywords);

        Document doc = null;
        try {

            doc = Jsoup.connect("https://www.target.com.au/search")
                    .data(requestParams)
                    .timeout(10*1000)
                    .get();

            TargetSearchResult searchResult = new TargetSearchResult(doc);
            List<TargetProduct> products = searchResult.getProducts();

            logger.info("Found {} Target products for keywords[{}].", products.size(), keywords);

            return new AsyncResult<>(products);
        } catch (IOException e) {
            logger.error("Unable to search for Target products", e);
            return new AsyncResult<>(new ArrayList<>());
        }
    }

    @Override
    public Store getStore() {
        return Target;
    }

}
