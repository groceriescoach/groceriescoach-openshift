package com.groceriescoach.babyandtoddlertown.service;


import com.groceriescoach.babyandtoddlertown.domain.BabyAndToddlerTownProduct;
import com.groceriescoach.babyandtoddlertown.domain.BabyAndToddlerTownSearchResult;
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

import static com.groceriescoach.core.domain.Store.BabyAndToddlerTown;

@Profile("online")
@Service
public class BabyAndToddlerTownService extends AbstractScrapingStoreSearchService<BabyAndToddlerTownProduct> {


    private static final Logger logger = LoggerFactory.getLogger(BabyAndToddlerTownService.class);

    @Override
    protected String getStoreSearchUrl() {
        return "https://www.babyandtoddlertown.com.au/search/show/all";
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
    protected List<BabyAndToddlerTownProduct> extractProducts(Document doc, GroceriesCoachSortType sortType) {
        BabyAndToddlerTownSearchResult searchResult = new BabyAndToddlerTownSearchResult(doc, sortType);
        return searchResult.getProducts();
    }

    @Override
    public Store getStore() {
        return BabyAndToddlerTown;
    }

}
