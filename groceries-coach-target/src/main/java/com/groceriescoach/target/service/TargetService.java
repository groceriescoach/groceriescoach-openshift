package com.groceriescoach.target.service;


import com.groceriescoach.core.domain.GroceriesCoachSortType;
import com.groceriescoach.core.domain.Store;
import com.groceriescoach.core.service.AbstractScrapingStoreSearchService;
import com.groceriescoach.target.domain.TargetProduct;
import com.groceriescoach.target.domain.TargetSearchResult;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.groceriescoach.core.domain.Store.Target;

@Profile("online")
@Service
public class TargetService extends AbstractScrapingStoreSearchService<TargetProduct> {


    private static final Logger logger = LoggerFactory.getLogger(TargetService.class);

    @Override
    protected String getStoreSearchUrl(String keywords, int page) {
        return "https://www.target.com.au/search";
    }

    @Override
    protected Map<String, String> getRequestParameters(int page) {
        Map<String, String> requestParams = new HashMap<>();
        requestParams.put("Nrpp", "90");
        if (page > 1) {
            requestParams.put("page", "" + (page - 1));
        }
        return requestParams;
    }

    @Override
    protected String getSearchKeywordParameter() {
        return "text";
    }

    @Override
    protected List<TargetProduct> extractProducts(Document doc, GroceriesCoachSortType sortType) {
        TargetSearchResult searchResult = new TargetSearchResult(doc, sortType);
        return searchResult.getProducts();
    }

    @Override
    public Store getStore() {
        return Target;
    }

}
