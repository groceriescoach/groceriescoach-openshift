package com.groceriescoach.babybunting.service;


import com.groceriescoach.babybunting.domain.BabyBuntingProduct;
import com.groceriescoach.babybunting.domain.BabyBuntingSearchResult;
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

import static com.groceriescoach.core.domain.Store.BabyBunting;

//@Profile("online")
@Service
public class BabyBuntingService extends AbstractScrapingStoreSearchService<BabyBuntingProduct> {

    private static final Logger logger = LoggerFactory.getLogger(BabyBuntingService.class);

    @Override
    protected String getStoreSearchUrl(String keywords, int page) {
        return "https://www.babybunting.com.au/catalogsearch/result/index";
    }

    @Override
    protected Map<String, String> getRequestParameters(int page) {
        Map<String, String> requestParams = new HashMap<>();
        requestParams.put("limit", "120");
        requestParams.put("p", "" + page);
        return requestParams;
    }

    @Override
    protected String getSearchKeywordParameter() {
        return "q";
    }

    @Override
    protected List<BabyBuntingProduct> extractProducts(Document doc, GroceriesCoachSortType sortType) {
        BabyBuntingSearchResult searchResult = new BabyBuntingSearchResult(doc, sortType);
        return searchResult.getProducts();
    }

    @Override
    public Store getStore() {
        return BabyBunting;
    }

}
