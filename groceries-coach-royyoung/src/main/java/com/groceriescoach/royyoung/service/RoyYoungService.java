package com.groceriescoach.royyoung.service;

import com.groceriescoach.core.domain.GroceriesCoachSortType;
import com.groceriescoach.core.domain.Store;
import com.groceriescoach.core.service.AbstractScrapingStoreSearchService;
import com.groceriescoach.royyoung.domain.RoyYoungProduct;
import com.groceriescoach.royyoung.domain.RoyYoungSearchResult;
import org.jsoup.nodes.Document;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.groceriescoach.core.domain.Store.RoyYoung;

@Profile("online")
@Service
public class RoyYoungService extends AbstractScrapingStoreSearchService<RoyYoungProduct> {

    @Override
    public Store getStore() {
        return RoyYoung;
    }

    @Override
    protected String getStoreSearchUrl(String keywords) {
        return "http://www.royyoungchemist.com.au/search/go";
    }

    @Override
    protected Map<String, String> getRequestParameters(int page) {
        Map<String, String> requestParameters = new HashMap<>();
        requestParameters.put("cnt", "300");
        return requestParameters;
    }

    @Override
    protected String getSearchKeywordParameter() {
        return "w";
    }

    @Override
    protected List<RoyYoungProduct> extractProducts(Document doc, GroceriesCoachSortType sortType) {
        RoyYoungSearchResult searchResult = new RoyYoungSearchResult(doc, sortType);
        return searchResult.getProducts();
    }
}
