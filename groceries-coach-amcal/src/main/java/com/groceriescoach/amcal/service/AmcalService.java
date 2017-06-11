package com.groceriescoach.amcal.service;


import com.groceriescoach.amcal.domain.AmcalProduct;
import com.groceriescoach.amcal.domain.AmcalSearchResult;
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

import static com.groceriescoach.core.domain.Store.Amcal;

@Profile("online")
@Service
public class AmcalService extends AbstractScrapingStoreSearchService<AmcalProduct> {


    private static final Logger logger = LoggerFactory.getLogger(AmcalService.class);

    @Override
    public Store getStore() {
        return Amcal;
    }

    @Override
    protected String getStoreSearchUrl() {
        return "https://www.amcal.com.au/SearchDisplay";
    }

    @Override
    protected Map<String, String> getRequestParameters() {
        Map<String, String> requestParams = new HashMap<>();
        requestParams.put("storeId", "10301");
        requestParams.put("catalogId", "10502");
        requestParams.put("sType", "SimpleSearch");
        requestParams.put("beginIndex", "0");
        requestParams.put("pageSize", "50");
        return requestParams;
    }

    @Override
    protected String getSearchKeywordParameter() {
        return "searchTerm";
    }

    @Override
    protected List<AmcalProduct> extractProducts(Document doc, GroceriesCoachSortType sortType) {
        AmcalSearchResult searchResult = new AmcalSearchResult(doc, sortType);
        return (List<AmcalProduct>) searchResult.getProducts();
    }
}
