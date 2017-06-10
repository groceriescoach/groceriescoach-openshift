package com.groceriescoach.bigw.service;


import com.groceriescoach.bigw.domain.BigWProduct;
import com.groceriescoach.bigw.domain.BigWSearchResult;
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

import static com.groceriescoach.core.domain.Store.BigW;

@Profile("online")
@Service
public class BigWService implements StoreSearchService<BigWProduct> {


    private static final Logger logger = LoggerFactory.getLogger(BigWService.class);

    @Async
    @Override
    public Future<List<BigWProduct>> search(String keywords, GroceriesCoachSortType sortType) {

        logger.debug("Searching Big W for {}.", keywords);

        Map<String, String> requestParams = new HashMap<>();

        requestParams.put("sort", "relevance");
        requestParams.put("pageSize", "144");

        requestParams.put("q", keywords);

        Document doc = null;
        try {

            doc = Jsoup.connect("https://www.bigw.com.au/search")
                    .data(requestParams)
                    .timeout(10*1000)
                    .maxBodySize(0)
                    .get();

            BigWSearchResult searchResult = new BigWSearchResult(doc, sortType);
            List<BigWProduct> products = searchResult.getProducts();

            logger.info("Found {} Big W products for keywords[{}].", products.size(), keywords);

            return new AsyncResult<>(products);
        } catch (IOException e) {
            logger.error("Unable to search for Big W products", e);
            return new AsyncResult<>(new ArrayList<>());
        }
    }

    @Override
    public Store getStore() {
        return BigW;
    }


}
