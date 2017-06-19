package com.groceriescoach.mrvitamins.service;

import com.groceriescoach.core.domain.GroceriesCoachSortType;
import com.groceriescoach.core.domain.Store;
import com.groceriescoach.core.service.AbstractScrapingStoreSearchService;
import com.groceriescoach.mrvitamins.domain.MrVitaminsProduct;
import com.groceriescoach.mrvitamins.domain.MrVitaminsSearchResult;
import org.jsoup.nodes.Document;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.groceriescoach.core.domain.Store.MrVitamins;

@Profile("online")
@Service
public class MrVitaminsService extends AbstractScrapingStoreSearchService<MrVitaminsProduct> {

    @Override
    public Store getStore() {
        return MrVitamins;
    }

    @Override
    protected String getStoreSearchUrl(String keywords) {
        return "https://www.mrvitamins.com.au/searchanise/result";
    }

    @Override
    protected Map<String, String> getRequestParameters() {
        return new HashMap<>();
    }

    @Override
    protected String getSearchKeywordParameter() {
        return "q";
    }

    @Override
    protected List<MrVitaminsProduct> extractProducts(Document doc, GroceriesCoachSortType sortType) {
        MrVitaminsSearchResult searchResult = new MrVitaminsSearchResult(doc, sortType);
        return searchResult.getProducts();
    }
}
