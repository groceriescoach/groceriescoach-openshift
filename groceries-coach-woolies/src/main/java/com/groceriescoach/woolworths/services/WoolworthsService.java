package com.groceriescoach.woolworths.services;


import com.groceriescoach.core.domain.GroceriesCoachSortType;
import com.groceriescoach.core.domain.Product;
import com.groceriescoach.core.domain.Store;
import com.groceriescoach.core.service.StoreSearchService;
import com.groceriescoach.woolworths.domain.WoolworthsSearchResult;
import com.groceriescoach.woolworths.domain.WoolworthsSortType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

import static com.groceriescoach.core.domain.Store.Woolworths;

@Profile("online")
@Service
public class WoolworthsService implements StoreSearchService {

    private final RestTemplate restTemplate;

    private static final Logger logger = LoggerFactory.getLogger(WoolworthsService.class);

    @Autowired
    public WoolworthsService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    @Async
    @Override
    public Future<List<Product>> search(String keywords, GroceriesCoachSortType sortType) {

        logger.info("Searching Woolworths for {}.", keywords);

        String trimmedKeywords = keywords.trim().replaceAll(" +", " ").replace(" ", "+");

        List<Product> products = getProductsForPage(trimmedKeywords, 2);

        logger.info("Found {} Woolworths products for keywords[{}].", products.size(), keywords);

        return new AsyncResult<>(products);
    }

    private List<Product> getProductsForPage(String keywords, int pages) {
        List<Product> products = new ArrayList<>();

        for (int i = 1; i <= pages; i++) {
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("https://www.woolworths.com.au/apis/ui/Search/products")
                    .queryParam("IsSpecial", "false")
                    .queryParam("PageNumber", i)
                    .queryParam("PageSize", "36")
                    .queryParam("SearchTerm", keywords)
                    .queryParam("SortType", WoolworthsSortType.Personalised.getKey());

            WoolworthsSearchResult searchResult = restTemplate.getForObject(builder.build().toUri(), WoolworthsSearchResult.class);
            products.addAll(searchResult.toProducts(keywords));
        }

        return products;
    }


    @Override
    public Store getStore() {
        return Woolworths;
    }

}
