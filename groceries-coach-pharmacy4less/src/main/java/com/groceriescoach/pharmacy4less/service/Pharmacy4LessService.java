package com.groceriescoach.pharmacy4less.service;


import com.groceriescoach.core.domain.GroceriesCoachSortType;
import com.groceriescoach.core.domain.Store;
import com.groceriescoach.core.service.StoreSearchService;
import com.groceriescoach.pharmacy4less.domain.Pharmacy4LessProduct;
import com.groceriescoach.pharmacy4less.domain.Pharmacy4LessSearchParameters;
import com.groceriescoach.pharmacy4less.domain.Pharmacy4LessSearchResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.concurrent.Future;

import static com.groceriescoach.core.domain.Store.Pharmacy4Less;

@Profile("online")
@Service
public class Pharmacy4LessService implements StoreSearchService<Pharmacy4LessProduct> {

    private final RestTemplate restTemplate;

    private static final Logger logger = LoggerFactory.getLogger(Pharmacy4LessService.class);

    @Autowired
    public Pharmacy4LessService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Async
    @Override
    public Future<List<Pharmacy4LessProduct>> search(String keywords, GroceriesCoachSortType sortType) {
        logger.debug("Searching Pharmacy 4 Less for {}.", keywords);

        List<Pharmacy4LessProduct> products = getProductsForPage(keywords, 1, sortType);

        logger.info("Found {} Pharmacy 4 Less products for keywords [{}].", products.size(), keywords);

        return new AsyncResult<>(products);
    }

    private List<Pharmacy4LessProduct> getProductsForPage(String keywords, int pages, GroceriesCoachSortType sortType) {


        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("https://6g4l8r87sh-dsn.algolia.net/1/indexes/*/queries")
                .queryParam("x-algolia-agent", "Algolia for vanilla JavaScript (lite) 3.19.2;instantsearch.js 1.8.16;Magento integration (1.8.1)")
                .queryParam("x-algolia-application-id", "6G4L8R87SH")
                .queryParam("x-algolia-api-key", "ZDkxMjI2NDE5NDFjOWE5ODg0ZjE2NmMyOWQ0MzgxZTVhZTlhOTJjYzczNTUxMTI1NzRhOTliZjhiNmI1YTcxNGZpbHRlcnM9Jm51bWVyaWNGaWx0ZXJzPXZpc2liaWxpdHlfc2VhcmNoJTNEMQ==");

        Pharmacy4LessSearchResult searchResult = restTemplate.postForObject(
                builder.build().toUri(),
                Pharmacy4LessSearchParameters.createParameters(keywords),
                Pharmacy4LessSearchResult.class);
        return searchResult.toProducts(sortType);
    }


    @Override
    public Store getStore() {
        return Pharmacy4Less;
    }

}
