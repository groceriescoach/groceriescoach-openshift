package com.groceriescoach.pharmacy4less.service;


import com.groceriescoach.core.domain.GroceriesCoachSortType;
import com.groceriescoach.core.domain.Store;
import com.groceriescoach.core.service.AbstractScrapingStoreSearchService;
import com.groceriescoach.pharmacy4less.domain.Pharmacy4LessProduct;
import com.groceriescoach.pharmacy4less.domain.Pharmacy4LessSearchResult;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.groceriescoach.core.domain.Store.Pharmacy4Less;

@Profile("online")
@Service
public class Pharmacy4LessService extends AbstractScrapingStoreSearchService<Pharmacy4LessProduct> {
    
    private static final Logger logger = LoggerFactory.getLogger(Pharmacy4LessService.class);

    @Override
    protected String getStoreSearchUrl(String keywords) {
        return "http://www.pharmacy4less.com.au/search/go";
    }

    @Override
    protected Map<String, String> getRequestParameters() {
        return new HashMap<>();
    }

    @Override
    protected String getSearchKeywordParameter() {
        return "w";
    }

    @Override
    protected List<Pharmacy4LessProduct> extractProducts(Document doc, GroceriesCoachSortType sortType) {
        Pharmacy4LessSearchResult searchResult = new Pharmacy4LessSearchResult(doc, sortType);
        return searchResult.getProducts();
    }

    @Override
    public Store getStore() {
        return Pharmacy4Less;
    }

}
