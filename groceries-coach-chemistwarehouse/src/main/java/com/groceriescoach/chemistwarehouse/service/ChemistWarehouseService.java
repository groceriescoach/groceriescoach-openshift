package com.groceriescoach.chemistwarehouse.service;


import com.groceriescoach.chemistwarehouse.domain.ChemistWarehouseProduct;
import com.groceriescoach.chemistwarehouse.domain.ChemistWarehouseSearchResult;
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

import static com.groceriescoach.core.domain.Store.ChemistWarehouse;

@Profile("online")
@Service
public class ChemistWarehouseService extends AbstractScrapingStoreSearchService<ChemistWarehouseProduct> {


    private static final Logger logger = LoggerFactory.getLogger(ChemistWarehouseService.class);

    @Override
    protected String getStoreSearchUrl(String keywords) {
        return "http://www.chemistwarehouse.com.au/search";
    }

    @Override
    protected Map<String, String> getRequestParameters() {
        Map<String, String> requestParams = new HashMap<>();
        requestParams.put("sort", "rank");
        requestParams.put("searchmode", "anywords");
        requestParams.put("size", "120");
        return requestParams;
    }

    @Override
    protected String getSearchKeywordParameter() {
        return "searchtext";
    }

    @Override
    protected List<ChemistWarehouseProduct> extractProducts(Document doc, GroceriesCoachSortType sortType) {
        ChemistWarehouseSearchResult chemistWarehouseSearchResult = new ChemistWarehouseSearchResult(doc, sortType);
        return chemistWarehouseSearchResult.getProducts();
    }

    @Override
    public Store getStore() {
        return ChemistWarehouse;
    }

    @Override
    protected String reformatKeywordsForStore(String keywords) {
        return StringUtils.trimToEmpty(keywords);
    }
}
