package com.groceriescoach.coles.service;


import com.groceriescoach.coles.domain.ColesSearchResult;
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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

import static com.groceriescoach.core.domain.Store.Coles;


@Profile("offline")
@Service
public class ColesOfflineService implements StoreSearchService {


    private static Map<String, String> DEFAULT_REQUEST_PARAMS = null;

    private static final Logger logger = LoggerFactory.getLogger(ColesOfflineService.class);

    @Async
    @Override
    public Future<List<Product>> search(String keywords, GroceriesCoachSortType sortType) {

        Document doc = null;
        try {
            String fileName = keywords.trim().replaceAll(" +", " ").replace(" ", "-");
            File colesHtml = new File("C:/projects/groceries-coach/groceries-coach-coles/src/test/resources/" + fileName + ".html");
            doc = Jsoup.parse(colesHtml, "UTF-8");
            ColesSearchResult colesSearchResult = new ColesSearchResult(doc);
            return new AsyncResult<>(colesSearchResult.toProducts());
        } catch (IOException e) {
            logger.error("Unable to search for Coles products", e);
            return new AsyncResult<>(new ArrayList<>());
        }
    }

    @Override
    public Store getStore() {
        return Coles;
    }


    private static Map<String, String> getDefaultRequestParams() {

        if (DEFAULT_REQUEST_PARAMS == null) {

            DEFAULT_REQUEST_PARAMS = new HashMap<>();

            DEFAULT_REQUEST_PARAMS.put("storeId", "10601");
            DEFAULT_REQUEST_PARAMS.put("catalogId", "10576");
            DEFAULT_REQUEST_PARAMS.put("langId", "-1");
            DEFAULT_REQUEST_PARAMS.put("beginIndex", "0");
            DEFAULT_REQUEST_PARAMS.put("sType", "SimpleSearch");
            DEFAULT_REQUEST_PARAMS.put("pageSize", "100");
            DEFAULT_REQUEST_PARAMS.put("pageNumber", "1");
            DEFAULT_REQUEST_PARAMS.put("refreshId", "searchView");
            DEFAULT_REQUEST_PARAMS.put("productView", "list");
            DEFAULT_REQUEST_PARAMS.put("browseView", "false");
            DEFAULT_REQUEST_PARAMS.put("context", "refreshController.refreshArea");
            DEFAULT_REQUEST_PARAMS.put("serviceId", "ColesSearchView");
            DEFAULT_REQUEST_PARAMS.put("expectedType", "text");
        }

        return DEFAULT_REQUEST_PARAMS;
    }

}
