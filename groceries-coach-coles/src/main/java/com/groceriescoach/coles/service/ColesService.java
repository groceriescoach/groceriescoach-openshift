package com.groceriescoach.coles.service;


import com.groceriescoach.coles.domain.ColesProduct;
import com.groceriescoach.coles.domain.ColesSearchResult;
import com.groceriescoach.core.domain.GroceriesCoachSortType;
import com.groceriescoach.core.domain.Store;
import com.groceriescoach.core.service.AbstractScrapingStoreSearchService;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.groceriescoach.core.domain.Store.Coles;


@Profile("online")
@Service
public class ColesService extends AbstractScrapingStoreSearchService<ColesProduct> {


    private static Map<String, String> DEFAULT_REQUEST_PARAMS = null;

    private static final Logger logger = LoggerFactory.getLogger(ColesService.class);

    @Override
    protected String getStoreSearchUrl(String keywords) {
        return "http://shop.coles.com.au/online/ColesSearchView";
    }

    @Override
    protected Map<String, String> getRequestParameters() {
        return getDefaultRequestParams();
    }

    @Override
    protected String getSearchKeywordParameter() {
        return "searchTerm";
    }

    @Override
    protected Map<String, String> getRequestCookies() {
        final Map<String, String> requestCookies = super.getRequestCookies();
        requestCookies.put("ColesSearchPageSizeCookie", "48");
        return requestCookies;
    }

    @Override
    protected List<ColesProduct> extractProducts(Document doc, GroceriesCoachSortType sortType) {
        ColesSearchResult colesSearchResult = new ColesSearchResult(doc, sortType);
        return colesSearchResult.getProducts();
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
            DEFAULT_REQUEST_PARAMS.put("orderBy", "");
        }

        return DEFAULT_REQUEST_PARAMS;
    }

}
