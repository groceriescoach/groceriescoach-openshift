package com.groceriescoach.babykingdom.service;


import com.groceriescoach.babykingdom.domain.BabyKingdomProduct;
import com.groceriescoach.babykingdom.domain.BabyKingdomSearchResult;
import com.groceriescoach.core.com.groceriescoach.core.utils.StringUtils;
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

import static com.groceriescoach.core.domain.Store.BabyKingdom;

@Profile("online")
@Service
public class BabyKingdomService extends AbstractScrapingStoreSearchService<BabyKingdomProduct> {


    private static final Logger logger = LoggerFactory.getLogger(BabyKingdomService.class);

    @Override
    protected String getStoreSearchUrl(String keywords) {
        return "https://www.babykingdom.com.au/shopsearch.asp?search=Yes";
    }

    @Override
    protected Map<String, String> getRequestParameters() {
        Map<String, String> requestParams = new HashMap<>();
        requestParams.put("b1", "search");
        return requestParams;
    }

    @Override
    protected String getSearchKeywordParameter() {
        return "keyword";
    }

    @Override
    protected List<BabyKingdomProduct> extractProducts(Document doc, GroceriesCoachSortType sortType) {
        BabyKingdomSearchResult searchResult = new BabyKingdomSearchResult(doc, sortType);
        return searchResult.getProducts();
    }

    @Override
    public Store getStore() {
        return BabyKingdom;
    }

    @Override
    protected String reformatKeywordsForStore(String keywords) {
        return StringUtils.trimToEmpty(keywords);
    }
}
