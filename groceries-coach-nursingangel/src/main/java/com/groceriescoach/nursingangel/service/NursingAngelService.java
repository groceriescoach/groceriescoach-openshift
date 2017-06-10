package com.groceriescoach.nursingangel.service;


import com.groceriescoach.core.domain.GroceriesCoachSortType;
import com.groceriescoach.core.domain.Store;
import com.groceriescoach.core.service.StoreSearchService;
import com.groceriescoach.nursingangel.domain.NursingAngelProduct;
import com.groceriescoach.nursingangel.domain.NursingAngelSearchResult;
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

import static com.groceriescoach.core.domain.Store.NursingAngel;

@Profile("online")
@Service
public class NursingAngelService implements StoreSearchService<NursingAngelProduct> {


    private static final Logger logger = LoggerFactory.getLogger(NursingAngelService.class);

    @Async
    @Override
    public Future<List<NursingAngelProduct>> search(String keywords, GroceriesCoachSortType sortType) {

        logger.debug("Searching Nursing Angel for {}.", keywords);

//        /?kw=medela&pgnum=2&rf=kw


        Map<String, String> requestParams = new HashMap<>();

        requestParams.put("kw", keywords);
        requestParams.put("pgnum", "1");
        requestParams.put("rf", "kw");

        Document doc = null;
        try {
            doc = Jsoup.connect("http://www.nursingangel.com.au")
                    .data(requestParams)
                    .timeout(10*1000)
                    .get();

            NursingAngelSearchResult searchResult = new NursingAngelSearchResult(doc, sortType);
            List<NursingAngelProduct> products = searchResult.getProducts();

            logger.info("Found {} Nursing Angel products for keywords[{}].", products.size(), keywords);

            return new AsyncResult<>(products);
        } catch (IOException e) {
            logger.error("Unable to search for Nursing Angel products", e);
            return new AsyncResult<>(new ArrayList<>());
        }
    }

    @Override
    public Store getStore() {
        return NursingAngel;
    }

}
