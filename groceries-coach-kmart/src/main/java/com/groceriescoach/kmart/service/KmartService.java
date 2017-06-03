package com.groceriescoach.kmart.service;


import com.groceriescoach.core.domain.GroceriesCoachSortType;
import com.groceriescoach.core.domain.Store;
import com.groceriescoach.core.service.StoreSearchService;
import com.groceriescoach.kmart.domain.KmartProduct;
import com.groceriescoach.kmart.domain.KmartSearchResult;
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

import static com.groceriescoach.core.domain.Store.Kmart;

@Profile("online")
@Service
public class KmartService implements StoreSearchService {


    private static final Logger logger = LoggerFactory.getLogger(KmartService.class);

    @Async
    @Override
    public Future<List<KmartProduct>> search(String keywords, GroceriesCoachSortType sortType) {

        logger.debug("Searching Kmart for {}.", keywords);

        Map<String, String> requestParams = new HashMap<>();

        requestParams.put("searchTerm", keywords);
        requestParams.put("storeId", "10701");
        requestParams.put("catalogId", "10102");
        requestParams.put("pageSize", "90");
        requestParams.put("beginIndex", "0");
        requestParams.put("sType", "SimpleSearch");
        requestParams.put("resultCatEntryType", "2");
        requestParams.put("showResultsPage", "true");
        requestParams.put("searchSource", "Q");

        //  http://www.kmart.com.au/webapp/wcs/stores/servlet/SearchDisplay?searchTerm=socks&storeId=10701&catalogId=10102&pageSize=90&beginIndex=0&sType=SimpleSearch&resultCatEntryType=2&showResultsPage=true&searchSource=Q

        Document doc = null;
        try {
            final Connection.Response response = Jsoup.connect("http://www.kmart.com.au/webapp/wcs/stores/servlet/SearchDisplay")
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36")
                    .header("Accept", "*/*")
                    .header("Accept-Encoding", "gzip, deflate")
                    .header("Cache-Control", "no-cache")
                    .maxBodySize(0)
                    .timeout(10*1000)
                    .execute();

            logger.debug("URL: {}, StatusCode: {}, StatusMessage: {}", response.url(), response.statusCode(), response.statusMessage());

            doc = response.parse();

            KmartSearchResult searchResult = new KmartSearchResult(doc);
            List<KmartProduct> products = searchResult.getProducts();

            logger.info("Found {} Kmart products for keywords[{}].", products.size(), keywords);

            return new AsyncResult<>(products);
        } catch (IOException e) {
            logger.error("Unable to search for Kmart products", e);
            return new AsyncResult<>(new ArrayList<>());
        }
    }

    @Override
    public Store getStore() {
        return Kmart;
    }

}
