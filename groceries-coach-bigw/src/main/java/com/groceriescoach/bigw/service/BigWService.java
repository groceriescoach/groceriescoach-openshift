package com.groceriescoach.bigw.service;


import com.groceriescoach.bigw.domain.BigWProduct;
import com.groceriescoach.bigw.domain.BigWSearchResult;
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

import static com.groceriescoach.core.domain.Store.BigW;

@Profile("online")
@Service
public class BigWService extends AbstractScrapingStoreSearchService<BigWProduct> {


    private static final Logger logger = LoggerFactory.getLogger(BigWService.class);

    @Override
    protected String getStoreSearchUrl(String keywords) {
        return "https://www.bigw.com.au/search";
    }

    @Override
    protected Map<String, String> getRequestParameters(int page) {
        Map<String, String> requestParams = new HashMap<>();
        requestParams.put("sort", "relevance");
        requestParams.put("pageSize", "144");
        requestParams.put("page", "" + (page-1));
        return requestParams;
    }

    @Override
    protected String getSearchKeywordParameter() {
        return "q";
    }

    @Override
    protected List<BigWProduct> extractProducts(Document doc, GroceriesCoachSortType sortType) {
        BigWSearchResult searchResult = new BigWSearchResult(doc, sortType);
        return searchResult.getProducts();
    }

    @Override
    public Store getStore() {
        return BigW;
    }

}
