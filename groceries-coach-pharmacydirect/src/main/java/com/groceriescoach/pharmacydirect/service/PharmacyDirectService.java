package com.groceriescoach.pharmacydirect.service;

import com.groceriescoach.core.domain.GroceriesCoachSortType;
import com.groceriescoach.core.domain.Store;
import com.groceriescoach.core.service.AbstractScrapingStoreSearchService;
import com.groceriescoach.pharmacydirect.domain.PharmacyDirectProduct;
import com.groceriescoach.pharmacydirect.domain.PharmacyDirectSearchResult;
import org.jsoup.nodes.Document;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.groceriescoach.core.domain.Store.PharmacyDirect;

@Profile("online")
@Service
public class PharmacyDirectService extends AbstractScrapingStoreSearchService<PharmacyDirectProduct> {

    @Override
    public Store getStore() {
        return PharmacyDirect;
    }

    @Override
    protected String getStoreSearchUrl(String keywords) {
        return "http://shop.pharmacydirect.com.au/search";
    }

    @Override
    protected Map<String, String> getRequestParameters() {
        Map<String, String> requestParameters = new HashMap<>();
        requestParameters.put("cnt", "300");
        return requestParameters;
    }

    @Override
    protected String getSearchKeywordParameter() {
        return "w";
    }

    @Override
    protected List<PharmacyDirectProduct> extractProducts(Document doc, GroceriesCoachSortType sortType) {
        PharmacyDirectSearchResult searchResult = new PharmacyDirectSearchResult(doc, sortType);
        return searchResult.getProducts();
    }
}
