package com.groceriescoach.fruitezy.service;

import com.groceriescoach.core.domain.GroceriesCoachSortType;
import com.groceriescoach.core.domain.Store;
import com.groceriescoach.core.service.AbstractScrapingStoreSearchService;
import com.groceriescoach.fruitezy.domain.FruitEzyProduct;
import com.groceriescoach.fruitezy.domain.FruitEzySearchResult;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.groceriescoach.core.domain.Store.FruitEzy;

@Service
public class FruitEzyService extends AbstractScrapingStoreSearchService<FruitEzyProduct> {

    @Override
    public Store getStore() {
        return FruitEzy;
    }

    @Override
    protected String getStoreSearchUrl(String keywords, int page) {
        return "https://fruitezy.com.au";
    }

    @Override
    protected Map<String, String> getRequestParameters(int page) {
        final HashMap<String, String> requestParameters = new HashMap<>();
        requestParameters.put("post_type", "product");
        return requestParameters;
    }

    @Override
    protected String getSearchKeywordParameter() {
        return "s";
    }

    @Override
    protected List<FruitEzyProduct> extractProducts(Document doc, GroceriesCoachSortType sortType) {
        FruitEzySearchResult searchResult = new FruitEzySearchResult(doc, sortType);
        return searchResult.getProducts();
    }
}
