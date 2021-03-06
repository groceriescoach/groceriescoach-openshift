package com.groceriescoach.babyvillage.service;


import com.groceriescoach.babyvillage.domain.BabyVillageProduct;
import com.groceriescoach.babyvillage.domain.BabyVillageRequestParameters;
import com.groceriescoach.babyvillage.domain.BabyVillageSearchResult;
import com.groceriescoach.core.domain.GroceriesCoachSortType;
import com.groceriescoach.core.domain.Store;
import com.groceriescoach.core.service.StoreSearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.Future;

import static com.groceriescoach.core.domain.Store.BabyVillage;

//@Profile("online")
@Service
public class BabyVillageService implements StoreSearchService<BabyVillageProduct> {

    private final RestTemplate restTemplate;


    private static final Logger logger = LoggerFactory.getLogger(BabyVillageService.class);

    @Autowired
    public BabyVillageService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Async
    @Override
    public Future<List<BabyVillageProduct>> search(String keywords, GroceriesCoachSortType sortType, int page) {

        logger.debug("Searching Baby Village for {}.", keywords);

        List<BabyVillageProduct> products = getProductsForPage(keywords, page, sortType);

        logger.info("Found {} Baby Village products for keywords [{}].", products.size(), keywords);

        return new AsyncResult<>(products);
    }

    private List<BabyVillageProduct> getProductsForPage(String keywords, int page, GroceriesCoachSortType sortType) {
        BabyVillageSearchResult searchResult = restTemplate.postForObject(
                "https://www.babyvillage.com.au/api/product/searchproducts",
                BabyVillageRequestParameters.createParameters(keywords, page),
                BabyVillageSearchResult.class);
        return searchResult.toProducts(sortType);
    }

    @Override
    public Store getStore() {
        return BabyVillage;
    }

}
