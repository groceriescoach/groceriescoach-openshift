package com.groceriescoach.terrywhite.service;

import com.groceriescoach.core.domain.GroceriesCoachSortType;
import com.groceriescoach.core.domain.Store;
import com.groceriescoach.core.service.AbstractScrapingStoreSearchService;
import com.groceriescoach.terrywhite.domain.TerryWhiteProduct;
import com.groceriescoach.terrywhite.domain.TerryWhiteSearchResult;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.groceriescoach.core.domain.Store.TerryWhite;

@Profile("online")
@Service
public class TerryWhiteService extends AbstractScrapingStoreSearchService<TerryWhiteProduct> {


    private static final Logger logger = LoggerFactory.getLogger(TerryWhiteService.class);

    @Override
    public Store getStore() {
        return TerryWhite;
    }

    @Override
    protected String getStoreSearchUrl(String keywords) {
        return "https://shop.terrywhitechemmart.com.au/catalogsearch/result";
    }

    @Override
    protected Map<String, String> getRequestParameters(int page) {
        Map<String, String> requestParams = new HashMap<>();
        requestParams.put("limit", "48");
        requestParams.put("p", "" + page);
        return requestParams;
    }

    @Override
    protected String getSearchKeywordParameter() {
        return "q";
    }

    @Override
    protected List<TerryWhiteProduct> extractProducts(Document doc, GroceriesCoachSortType sortType) {
        TerryWhiteSearchResult searchResult = new TerryWhiteSearchResult(doc, sortType);
        return (List<TerryWhiteProduct>) searchResult.getProducts();
    }
}
