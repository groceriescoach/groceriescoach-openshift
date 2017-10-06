package com.groceriescoach.woolworths.services;


import com.groceriescoach.core.domain.GroceriesCoachSortType;
import com.groceriescoach.core.domain.Store;
import com.groceriescoach.core.service.StoreSearchService;
import com.groceriescoach.woolworths.domain.WoolworthsProduct;
import com.groceriescoach.woolworths.domain.WoolworthsRequestParameters;
import com.groceriescoach.woolworths.domain.WoolworthsSearchResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

import static com.groceriescoach.core.domain.Store.Woolworths;

@Profile("online")
@Service
public class WoolworthsService implements StoreSearchService<WoolworthsProduct> {

    private final RestTemplate restTemplate;

    private static final Logger logger = LoggerFactory.getLogger(WoolworthsService.class);

    @Autowired
    public WoolworthsService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    @Async
    @Override
    public Future<List<WoolworthsProduct>> search(String keywords, GroceriesCoachSortType sortType, int page) {

        logger.info("Searching Woolworths for {}, page {}.", keywords, page);

        List<WoolworthsProduct> products = getProductsForPage(keywords, sortType, page);

        logger.info("Found {} Woolworths products for keywords [{}].", products.size(), keywords);

        return new AsyncResult<>(products);
    }

    private List<WoolworthsProduct> getProductsForPage(String keywords, GroceriesCoachSortType sortType, int page) {

        List<WoolworthsProduct> products = new ArrayList<>();
        WoolworthsSearchResult searchResult =
                restTemplate.postForObject(
                        "https://www.woolworths.com.au/apis/ui/Search/products",
                        WoolworthsRequestParameters.createParameters(keywords, page),
                        WoolworthsSearchResult.class);
        products.addAll(searchResult.toWoolworthsProducts(sortType));
        return products;
    }


    @Override
    public Store getStore() {
        return Woolworths;
    }

}
