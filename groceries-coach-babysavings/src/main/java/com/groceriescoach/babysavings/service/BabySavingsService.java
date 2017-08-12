package com.groceriescoach.babysavings.service;

import com.groceriescoach.babysavings.domain.BabySavingsProduct;
import com.groceriescoach.babysavings.domain.BabySavingsSearchResult;
import com.groceriescoach.core.com.groceriescoach.core.utils.StringUtils;
import com.groceriescoach.core.domain.GroceriesCoachSortType;
import com.groceriescoach.core.domain.Store;
import com.groceriescoach.core.service.AbstractScrapingStoreSearchService;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.groceriescoach.core.domain.Store.BabySavings;

@Service
public class BabySavingsService extends AbstractScrapingStoreSearchService<BabySavingsProduct> {

    @Override
    protected String getStoreSearchUrl(String keywords) {
        return "https://www.babysavings.com.au/catalogsearch/result/index";
    }

    @Override
    protected Map<String, String> getRequestParameters(int page) {
        Map<String, String> requestParameters = new HashMap<>();
        requestParameters.put("limit", "30");
        requestParameters.put("p", "" + page);
        return requestParameters;
    }

    @Override
    protected String getSearchKeywordParameter() {
        return "q";
    }

    @Override
    protected List<BabySavingsProduct> extractProducts(Document doc, GroceriesCoachSortType sortType) {
        BabySavingsSearchResult babySavingsSearchResult = new BabySavingsSearchResult(doc, sortType);
        return babySavingsSearchResult.getProducts();
    }

    @Override
    public Store getStore() {
        return BabySavings;
    }

    @Override
    protected String reformatKeywordsForStore(String keywords) {
        return StringUtils.trimToEmpty(keywords);
    }

}
