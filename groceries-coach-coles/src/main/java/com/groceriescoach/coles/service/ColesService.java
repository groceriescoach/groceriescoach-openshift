package com.groceriescoach.coles.service;


import com.groceriescoach.coles.domain.ColesProduct;
import com.groceriescoach.coles.domain.ColesSearchResult;
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

import static com.groceriescoach.core.domain.Store.Coles;


@Profile("online")
@Service
public class ColesService implements StoreSearchService {


    private static Map<String, String> DEFAULT_REQUEST_PARAMS = null;

    private static final Logger logger = LoggerFactory.getLogger(ColesService.class);

    @Async
    @Override
    public Future<List<ColesProduct>> search(String keywords, GroceriesCoachSortType sortType) {

        logger.debug("Searching Coles for {}.", keywords);

        Map<String, String> requestParams = new HashMap<>();
        requestParams.putAll(getDefaultRequestParams());

        requestParams.put("orderBy", "");
        requestParams.put("searchTerm", keywords);

        Document doc = null;
        try {

            doc = Jsoup.connect("http://shop.coles.com.au/online/ColesSearchView")
                    .data(requestParams)
                    .cookie("ColesSearchPageSizeCookie", "48")
                    .timeout(10*1000)
                    .get();

            ColesSearchResult colesSearchResult = new ColesSearchResult(doc, sortType);

            List<ColesProduct> products = colesSearchResult.getProducts();
            logger.info("Found {} Coles products for keywords[{}].", products.size(), keywords);

            return new AsyncResult<>(products);
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
