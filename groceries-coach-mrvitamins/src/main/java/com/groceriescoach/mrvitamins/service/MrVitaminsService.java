package com.groceriescoach.mrvitamins.service;

import com.groceriescoach.core.domain.GroceriesCoachSortType;
import com.groceriescoach.core.domain.Store;
import com.groceriescoach.core.service.StoreSearchService;
import com.groceriescoach.mrvitamins.domain.MrVitaminsProduct;
import com.groceriescoach.mrvitamins.domain.MrVitaminsSearchResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.concurrent.Future;

import static com.groceriescoach.core.domain.Store.MrVitamins;

//@Profile("online")
@Service
public class MrVitaminsService implements StoreSearchService<MrVitaminsProduct> {

    private final RestTemplate restTemplate;

    private static final Logger logger = LoggerFactory.getLogger(MrVitaminsService.class);

    @Autowired
    public MrVitaminsService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Store getStore() {
        return MrVitamins;
    }

    @Override
    public Future<List<MrVitaminsProduct>> search(String keywords, GroceriesCoachSortType sortType, int page) {
        logger.debug("Searching Mr. Vitamins for {}.", keywords);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("https://www.searchanise.com/getresults")
                .queryParam("maxResults", "100")
                .queryParam("startIndex", 0)
                .queryParam("sortOrder", "desc")
                .queryParam("q", keywords)
                .queryParam("sortBy", "relevance")
                .queryParam("api_key", "2t9v1p0n4a");


        MrVitaminsSearchResult searchResult =
                restTemplate.getForObject(builder.build().toUri(), MrVitaminsSearchResult.class);
        List<MrVitaminsProduct> products = searchResult.toProducts(sortType);

        logger.info("Found {} Mr. Vitamins products for keywords [{}].", products.size(), keywords);

        return new AsyncResult<>(products);
    }

}
