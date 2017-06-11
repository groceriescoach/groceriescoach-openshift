package com.groceriescoach.priceline.service;

import com.groceriescoach.core.domain.GroceriesCoachSortType;
import com.groceriescoach.core.domain.Store;
import com.groceriescoach.core.service.AbstractScrapingStoreSearchService;
import com.groceriescoach.priceline.domain.PricelineProduct;
import com.groceriescoach.priceline.domain.PricelineSearchResult;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.groceriescoach.core.domain.Store.Priceline;

@Profile("online")
@Service
public class PricelineService extends AbstractScrapingStoreSearchService<PricelineProduct> {

    private static final Logger logger = LoggerFactory.getLogger(PricelineService.class);

    @Override
    protected String getStoreSearchUrl() {
        return "https://www.priceline.com.au/search";
    }

    @Override
    protected Map<String, String> getRequestParameters() {
        Map<String, String> requestParams = new HashMap<>();
        requestParams.put("limit", "40");
        return requestParams;
    }

    @Override
    protected String getSearchKeywordParameter() {
        return "q";
    }

    @Override
    protected List<PricelineProduct> extractProducts(Document doc, GroceriesCoachSortType sortType) {
        PricelineSearchResult searchResult = new PricelineSearchResult(doc, sortType);
        return searchResult.getProducts();
    }

    @Override
    public Store getStore() {
        return Priceline;
    }

}
