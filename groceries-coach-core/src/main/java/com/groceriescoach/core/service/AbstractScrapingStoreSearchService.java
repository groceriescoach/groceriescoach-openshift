package com.groceriescoach.core.service;

import com.groceriescoach.core.domain.GroceriesCoachSortType;
import com.groceriescoach.core.domain.Product;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

public abstract class AbstractScrapingStoreSearchService<P extends Product> implements StoreSearchService {

    private static final Logger logger = LoggerFactory.getLogger(AbstractScrapingStoreSearchService.class);

    protected abstract String getStoreSearchUrl();

    protected abstract Map<String, String> getRequestParameters();

    protected Map<String, String> getRequestCookies() {
        return new HashMap<>();
    }

    protected abstract String getSearchKeywordParameter();

    protected abstract List<P> extractProducts(Document doc, GroceriesCoachSortType sortType);

    @Async
    @Override
    public Future<List<P>> search(String keywords, GroceriesCoachSortType sortType) {

        logger.debug("Searching {} for {}.", getStore(), keywords);

        Map<String, String> requestParams = getRequestParameters();
        requestParams.put(getSearchKeywordParameter(), keywords);

        Document doc;
        try {

            final Connection.Response response = Jsoup.connect(getStoreSearchUrl())
                    .data(requestParams)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36")
                    .header("Accept", "*/*")
                    .header("Accept-Encoding", "gzip, deflate")
                    .header("Cache-Control", "no-cache")
                    .cookies(getRequestCookies())
                    .maxBodySize(0)
                    .timeout(10 * 1000)
                    .execute();

            doc = response.parse();
            List<P> products = extractProducts(doc, sortType);

            logger.info("Found {} {} products for keywords[{}].", products.size(), getStore(), keywords);

            return new AsyncResult<>(products);
        } catch (IOException e) {
            logger.error("Unable to search for " + getStore() + " products", e);
            return new AsyncResult<>(new ArrayList<>());
        }
    }

}
