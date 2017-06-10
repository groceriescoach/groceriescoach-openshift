package com.groceriescoach.pharmacy4less.service;


import com.groceriescoach.core.domain.GroceriesCoachSortType;
import com.groceriescoach.core.domain.Store;
import com.groceriescoach.core.service.StoreSearchService;
import com.groceriescoach.pharmacy4less.domain.Pharmacy4LessProduct;
import com.groceriescoach.pharmacy4less.domain.Pharmacy4LessSearchResult;
import org.jsoup.Connection;
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

import static com.groceriescoach.core.domain.Store.Pharmacy4Less;

@Profile("online")
@Service
public class Pharmacy4LessService implements StoreSearchService<Pharmacy4LessProduct> {


    private static final Logger logger = LoggerFactory.getLogger(Pharmacy4LessService.class);

    @Async
    @Override
    public Future<List<Pharmacy4LessProduct>> search(String keywords, GroceriesCoachSortType sortType) {

        logger.debug("Searching Pharmacy 4 Less for {}.", keywords);

        Map<String, String> requestParams = new HashMap<>();

        requestParams.put("w", keywords);

        Document doc = null;
        try {
            Connection.Response response = Jsoup.connect("http://www.pharmacy4less.com.au/search/go")
                    .data(requestParams)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36")
                    .header("Accept", "*/*")
                    .header("Accept-Encoding", "gzip, deflate")
                    .header("Cache-Control", "no-cache")
                    .maxBodySize(0)
                    .timeout(10*1000)
                    .execute();

            doc = response.parse();

            Pharmacy4LessSearchResult searchResult = new Pharmacy4LessSearchResult(doc, sortType);
            List<Pharmacy4LessProduct> products = searchResult.getProducts();

            logger.info("Found {} Pharmacy 4 Less products for keywords[{}].", products.size(), keywords);

            return new AsyncResult<>(products);
        } catch (IOException e) {
            logger.error("Unable to search for Pharmacy 4 Less products", e);
            return new AsyncResult<>(new ArrayList<>());
        }
    }

    @Override
    public Store getStore() {
        return Pharmacy4Less;
    }

}
