package com.groceriescoach.controllers;


import com.groceriescoach.core.com.groceriescoach.core.utils.CollectionUtils;
import com.groceriescoach.core.com.groceriescoach.core.utils.StringUtils;
import com.groceriescoach.core.domain.GroceriesCoachSearchResults;
import com.groceriescoach.core.domain.GroceriesCoachSortType;
import com.groceriescoach.core.domain.KVP;
import com.groceriescoach.core.domain.Store;
import com.groceriescoach.service.ProductSearchService;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.groceriescoach.controllers.ApiUrls.ProductSearch.*;

@RestController
public class ProductSearchController {

    private final ProductSearchService productSearchService;

    @Autowired
    public ProductSearchController(ProductSearchService productSearchService) {
        this.productSearchService = productSearchService;
    }

    private static final Logger logger = LoggerFactory.getLogger(ProductSearchController.class);

    @RequestMapping(value = SEARCH_WITH_PHRASE_URL)
    public ResponseEntity<GroceriesCoachResponse<GroceriesCoachSearchResults>> searchWithPhrase(
            @RequestParam(value = SEARCH_PHRASE, required = true) String searchPhrase,
            @RequestHeader Map<String, String> headers)
            throws IOException {

        logger.info("Received search request: search phrase = [{}], headers: [{}].", searchPhrase, headers);

        final GroceriesCoachSearchResults searchResults = productSearchService.search(searchPhrase);
        logger.info("Returning {} results.", searchResults.size());

        final GroceriesCoachResponse<GroceriesCoachSearchResults> response = new GroceriesCoachResponse<>(searchResults);

        if (searchResults.isEmpty()) {
            response.addMessage("No results were found, please try a different search.");
        } else {
            response.addMessage("Found " + searchResults.size() + " results.");
        }

        return ResponseEntity.ok(response);
    }

    @RequestMapping(value = SEARCH_URL)
    public ResponseEntity<GroceriesCoachResponse<GroceriesCoachSearchResults>> search(
            @RequestParam(value = KEYWORDS) String keywords,
            @RequestParam(value = STORES, required = false) String[] storeKeys,
            @RequestParam(value = SORT_BY, required = false) String sortBy,
            @RequestParam(value = ALL_SEARCH_KEYWORDS_REQUIRED, required = false) boolean allSearchKeywordsRequired,
            @RequestHeader Map<String, String> headers)
            throws IOException {

        logger.info("Received search request: keywords = [{}], stores = [{}], sortBy = [{}], allSearchKeywordsRequired = [{}], headers: [{}].",
                keywords, storeKeys, sortBy, allSearchKeywordsRequired, headers);

        List<String> messages = new ArrayList<>();

        if (storeKeys == null || ArrayUtils.isEmpty(storeKeys)) {
            messages.add("Please select at least one shop to search.");
        }

        if (StringUtils.isBlank(sortBy)) {
            messages.add("Please select how you want results sorted.");
        }

        if (CollectionUtils.isNotEmpty(messages)) {
            GroceriesCoachResponse response = new GroceriesCoachResponse();
            response.addMessages(messages);
            return ResponseEntity.ok(response);
        }

        GroceriesCoachSortType sortType = GroceriesCoachSortType.fromKey(sortBy);
        List<Store> stores = new ArrayList<>();
        if (storeKeys != null && storeKeys.length > 0) {
            stores.addAll(Store.fromStoreKeys(storeKeys));
        }

        final GroceriesCoachSearchResults searchResult = productSearchService.search(keywords, stores, sortType, allSearchKeywordsRequired);

        logger.info("Returning {} results.", searchResult.size());

        final GroceriesCoachResponse<GroceriesCoachSearchResults> response = new GroceriesCoachResponse<>(searchResult);

        if (searchResult.isEmpty()) {
            response.addMessage("No results were found, please try a different search.");
        } else {
            response.addMessage("Found " + searchResult.size() + " results.");
        }

        return ResponseEntity.ok(response);
    }


    @RequestMapping(value = SORT_TYPES_URL)
    public ResponseEntity<List<KVP>> getSortTypes() {
        return ResponseEntity.ok(KVP.fromMap(GroceriesCoachSortType.getMap()));
    }


    @RequestMapping(value = STORES_URL)
    public ResponseEntity<Map<String, List<KVP>>> getStores() {
        final Map<String, List<KVP>> storeTypeToStoresMap = new HashMap<>();

        for (Map.Entry<String, Map<String, String>> entry: Store.getMap().entrySet()) {
            storeTypeToStoresMap.put(entry.getKey(), KVP.fromMap(entry.getValue()));
        }
        return ResponseEntity.ok(storeTypeToStoresMap);
    }

}
