package com.groceriescoach.thepharmacy.service;

import com.groceriescoach.core.domain.GroceriesCoachSortType;
import com.groceriescoach.core.domain.Store;
import com.groceriescoach.core.service.AbstractScrapingStoreSearchService;
import com.groceriescoach.thepharmacy.domain.ThePharmacyProduct;
import com.groceriescoach.thepharmacy.domain.ThePharmacySearchResult;
import org.jsoup.nodes.Document;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.groceriescoach.core.domain.Store.ThePharmacy;

@Profile("online")
@Service
public class ThePharmacyService extends AbstractScrapingStoreSearchService<ThePharmacyProduct> {

    @Override
    public Store getStore() {
        return ThePharmacy;
    }

    @Override
    protected String getStoreSearchUrl(String keywords, int page) {
        return "https://www.thepharmacy.com.au/catalogsearch/result/index";
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
    protected List<ThePharmacyProduct> extractProducts(Document doc, GroceriesCoachSortType sortType) {
        ThePharmacySearchResult searchResult = new ThePharmacySearchResult(doc, sortType);
        return searchResult.getProducts();
    }
}
