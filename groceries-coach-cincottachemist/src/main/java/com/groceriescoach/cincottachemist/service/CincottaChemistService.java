package com.groceriescoach.cincottachemist.service;

import com.groceriescoach.cincottachemist.domain.CincottaChemistProduct;
import com.groceriescoach.cincottachemist.domain.CincottaChemistSearchResult;
import com.groceriescoach.core.domain.GroceriesCoachSortType;
import com.groceriescoach.core.domain.Store;
import com.groceriescoach.core.service.AbstractScrapingStoreSearchService;
import org.jsoup.nodes.Document;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.groceriescoach.core.domain.Store.CincottaChemist;

@Profile("online")
@Service
public class CincottaChemistService extends AbstractScrapingStoreSearchService<CincottaChemistProduct> {

    @Override
    public Store getStore() {
        return CincottaChemist;
    }

    @Override
    protected String getStoreSearchUrl(String keywords) {
        return "http://www.cincottachemist.com.au/q/" + keywords;
    }

    @Override
    protected Map<String, String> getRequestParameters() {
        final HashMap<String, String> requestParameters = new HashMap<>();
        requestParameters.put("p", "1");
        return requestParameters;
    }

    @Override
    protected String getSearchKeywordParameter() {
        return null;
    }

    @Override
    protected List<CincottaChemistProduct> extractProducts(Document doc, GroceriesCoachSortType sortType) {
        CincottaChemistSearchResult searchResult = new CincottaChemistSearchResult(doc, sortType);
        return searchResult.getProducts();
    }
}
