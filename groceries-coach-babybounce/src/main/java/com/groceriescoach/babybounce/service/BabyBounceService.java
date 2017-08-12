package com.groceriescoach.babybounce.service;

import com.groceriescoach.babybounce.domain.BabyBounceProduct;
import com.groceriescoach.babybounce.domain.BabyBounceSearchResult;
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

import static com.groceriescoach.core.domain.Store.BabyBounce;

@Profile("online")
@Service
public class BabyBounceService extends AbstractScrapingStoreSearchService<BabyBounceProduct> {

    private static final Logger logger = LoggerFactory.getLogger(BabyBounceService.class);

    @Override
    protected String getStoreSearchUrl(String keywords) {
        return "https://babybounce.com.au/catalogsearch/result";
    }

    @Override
    protected Map<String, String> getRequestParameters(int page) {
        Map<String, String> requestParams = new HashMap<>();
        requestParams.put("limit", "all");
        return requestParams;
    }

    @Override
    protected String getSearchKeywordParameter() {
        return "q";
    }

    @Override
    protected List<BabyBounceProduct> extractProducts(Document doc, GroceriesCoachSortType sortType) {
        BabyBounceSearchResult searchResult = new BabyBounceSearchResult(doc, sortType);
        return searchResult.getProducts();
    }

    @Override
    public Store getStore() {
        return BabyBounce;
    }

    @Override
    protected String reformatKeywordsForStore(String keywords) {
        return StringUtils.trimToEmpty(keywords);
    }


}
