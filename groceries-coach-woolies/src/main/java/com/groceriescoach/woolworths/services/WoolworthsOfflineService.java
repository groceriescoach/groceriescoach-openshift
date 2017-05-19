package com.groceriescoach.woolworths.services;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.groceriescoach.core.domain.GroceriesCoachSortType;
import com.groceriescoach.core.domain.Product;
import com.groceriescoach.core.domain.Store;
import com.groceriescoach.core.service.StoreSearchService;
import com.groceriescoach.woolworths.domain.WoolworthsSearchResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

import static com.groceriescoach.core.domain.Store.Woolworths;

@Profile("offline")
@Service
public class WoolworthsOfflineService implements StoreSearchService {

    private final RestTemplate restTemplate;

    private static final Logger logger = LoggerFactory.getLogger(WoolworthsOfflineService.class);

    @Autowired
    public WoolworthsOfflineService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Future<List<Product>> search(String keywords, GroceriesCoachSortType sortType) {

        String fileName = keywords.trim().replaceAll(" +", " ").replace(" ", "-");
        File file = new File("C:/projects/groceries-coach/groceries-coach-woolies/src/test/resources/" + fileName + ".json");
        ObjectMapper mapper = new ObjectMapper();
        WoolworthsSearchResult searchResult = null;
        try {
            searchResult = mapper.reader().forType(WoolworthsSearchResult.class).readValue(file);
            return new AsyncResult<>(searchResult.toProducts(keywords));
        } catch (IOException e) {
            logger.error("Unable to parse file", e);
        }
        return new AsyncResult<>(new ArrayList<>());
    }

    @Override
    public Store getStore() {
        return Woolworths;
    }

}
