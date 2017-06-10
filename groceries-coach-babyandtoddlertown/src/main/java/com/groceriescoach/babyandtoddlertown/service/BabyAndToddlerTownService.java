package com.groceriescoach.babyandtoddlertown.service;


import com.groceriescoach.babyandtoddlertown.domain.BabyAndToddlerTownProduct;
import com.groceriescoach.babyandtoddlertown.domain.BabyAndToddlerTownSearchResult;
import com.groceriescoach.core.domain.GroceriesCoachSortType;
import com.groceriescoach.core.domain.Store;
import com.groceriescoach.core.service.StoreSearchService;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

import static com.groceriescoach.core.domain.Store.BabyAndToddlerTown;

@Profile("online")
@Service
public class BabyAndToddlerTownService implements StoreSearchService<BabyAndToddlerTownProduct> {


    private static final Logger logger = LoggerFactory.getLogger(BabyAndToddlerTownService.class);

    @Async
    @Override
    public Future<List<BabyAndToddlerTownProduct>> search(String keywords, GroceriesCoachSortType sortType) {

        logger.debug("Searching Baby and Toddler Town for {}.", keywords);

        Map<String, String> requestParams = new HashMap<>();

        requestParams.put("q", keywords);

        Document doc = null;
        try {


            Connection.Response response = Jsoup.connect("https://www.babyandtoddlertown.com.au/search/show/all")
                    .data(requestParams)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36")
                    .header("Accept", "*/*")
                    .header("Accept-Encoding", "gzip, deflate")
                    .header("Cache-Control", "no-cache")
                    .maxBodySize(0)
                    .timeout(10*1000)
                    .execute();

            doc = response.parse();

            BabyAndToddlerTownSearchResult searchResult = new BabyAndToddlerTownSearchResult(doc, sortType);
            List<BabyAndToddlerTownProduct> products = searchResult.getProducts();

            logger.info("Found {} Baby and Toddler Town products for keywords[{}].", products.size(), keywords);

            return new AsyncResult<>(products);
        } catch (IOException e) {
            logger.error("Unable to search for Baby and Toddler Town products", e);
            return new AsyncResult<>(new ArrayList<>());
        }
    }

    @Override
    public Store getStore() {
        return BabyAndToddlerTown;
    }

}
