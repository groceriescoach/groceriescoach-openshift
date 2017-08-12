package com.groceriescoach.nursingangel.service;


import com.groceriescoach.core.com.groceriescoach.core.utils.StringUtils;
import com.groceriescoach.core.domain.GroceriesCoachSortType;
import com.groceriescoach.core.domain.Store;
import com.groceriescoach.core.service.AbstractScrapingStoreSearchService;
import com.groceriescoach.nursingangel.domain.NursingAngelProduct;
import com.groceriescoach.nursingangel.domain.NursingAngelSearchResult;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.groceriescoach.core.domain.Store.NursingAngel;

@Profile("online")
@Service
public class NursingAngelService extends AbstractScrapingStoreSearchService<NursingAngelProduct> {


    private static final Logger logger = LoggerFactory.getLogger(NursingAngelService.class);

    @Override
    protected String getStoreSearchUrl(String keywords) {
        return "http://www.nursingangel.com.au";
    }

    @Override
    protected Map<String, String> getRequestParameters(int page) {
        Map<String, String> requestParams = new HashMap<>();
        requestParams.put("pgnum", "1");
        requestParams.put("rf", "kw");
        return requestParams;
    }

    @Override
    protected String getSearchKeywordParameter() {
        return "kw";
    }

    @Override
    protected List<NursingAngelProduct> extractProducts(Document doc, GroceriesCoachSortType sortType) {
        NursingAngelSearchResult searchResult = new NursingAngelSearchResult(doc, sortType);
        return searchResult.getProducts();
    }

    @Override
    public Store getStore() {
        return NursingAngel;
    }

    @Override
    protected String reformatKeywordsForStore(String keywords) {
        return StringUtils.trimToEmpty(keywords);
    }
}
