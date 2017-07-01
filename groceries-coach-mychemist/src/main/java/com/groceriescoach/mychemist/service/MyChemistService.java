package com.groceriescoach.mychemist.service;

import com.groceriescoach.core.domain.GroceriesCoachSortType;
import com.groceriescoach.core.domain.Store;
import com.groceriescoach.core.service.AbstractScrapingStoreSearchService;
import com.groceriescoach.mychemist.domain.MyChemistProduct;
import com.groceriescoach.mychemist.domain.MyChemistSearchResult;
import org.jsoup.nodes.Document;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.groceriescoach.core.domain.Store.MyChemist;

@Profile("online")
@Service
public class MyChemistService extends AbstractScrapingStoreSearchService<MyChemistProduct> {

    @Override
    public Store getStore() {
        return MyChemist ;
    }

    @Override
    protected String getStoreSearchUrl(String keywords) {
        return "http://www.mychemist.com.au/searchresult.asp";
    }

    @Override
    protected Map<String, String> getRequestParameters() {
        Map<String, String> requestParameters = new HashMap<>();
        requestParameters.put("perPage", "120");
        return requestParameters;
    }

    @Override
    protected String getSearchKeywordParameter() {
        return "terms";
    }

    @Override
    protected List<MyChemistProduct> extractProducts(Document doc, GroceriesCoachSortType sortType) {
        MyChemistSearchResult searchResult = new MyChemistSearchResult(doc, sortType);
        return searchResult.getProducts();
    }
}
