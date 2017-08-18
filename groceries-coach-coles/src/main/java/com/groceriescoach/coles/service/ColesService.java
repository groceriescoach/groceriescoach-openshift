package com.groceriescoach.coles.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.groceriescoach.coles.domain.ColesProduct;
import com.groceriescoach.coles.domain.ColesSearchResult;
import com.groceriescoach.core.com.groceriescoach.core.utils.StringUtils;
import com.groceriescoach.core.domain.GroceriesCoachSortType;
import com.groceriescoach.core.domain.Store;
import com.groceriescoach.core.service.AbstractStoreSearchService;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

import static com.groceriescoach.core.domain.Store.Coles;


@Profile("online")
@Service
public class ColesService extends AbstractStoreSearchService<ColesProduct> {


    private static final int PAGE_SIZE = 24;

    private static final ObjectMapper objectMapper = new ObjectMapper();


    private static final Logger logger = LoggerFactory.getLogger(ColesService.class);


    private String getStoreSearchUrl(String keywords) {
        return "https://shop.coles.com.au/online/COLRSSearchDisplay";
    }

    private Map<String, String> getRequestParameters(int page) {
        Map<String, String> requestParameters = new HashMap<>();
        requestParameters.put("storeId", "20601");
        requestParameters.put("catalogId", "10576");
        requestParameters.put("beginIndex", "" + ((page - 1) * PAGE_SIZE));
        return requestParameters;
    }

    private String getSearchKeywordParameter() {
        return "searchTerm";
    }

    private List<ColesProduct> extractProducts(Document doc, GroceriesCoachSortType sortType) {
        List<ColesProduct> products = new ArrayList<>();
        final Elements divs = doc.select(".row .products div");

        for (Element div : divs) {
            if (div.hasAttr("data-colrs-bind") && div.hasAttr("data-colrs-transformer")) {
                String searchResultContent = div.text();
                logger.info(searchResultContent);
                final ColesSearchResult searchResult;
                try {
                    searchResult = objectMapper.reader().forType(ColesSearchResult.class).readValue(searchResultContent);
                    products.addAll(searchResult.toColesProducts(sortType));
                } catch (IOException e) {
                    logger.error("Unable to search Coles", e);
                }
            }
        }
        return products;
    }

    @Override
    public Future<List<ColesProduct>> search(String keywords, GroceriesCoachSortType sortType, int page) {
        String reformattedKeywords = reformatKeywordsForStore(keywords);

        logger.debug("Searching {} for {}.", getStore(), reformattedKeywords);

        Map<String, String> requestParams = getRequestParameters(page);
        if (StringUtils.isNotBlank(getSearchKeywordParameter())) {
            requestParams.put(getSearchKeywordParameter(), reformattedKeywords);
        }

        Document doc;
        try {

            final Connection.Response response = Jsoup.connect(getStoreSearchUrl(reformattedKeywords))
                    .data(requestParams)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36")
                    .header("Accept", "*/*")
                    .header("Accept-Encoding", "gzip, deflate")
                    .header("Cache-Control", "no-cache")
                    .maxBodySize(0)
                    .timeout(10 * 1000)
                    .execute();

            logger.info("URL: {}", response.url());

            doc = response.parse();
            List<ColesProduct> products = extractProducts(doc, sortType);

            logger.info("Found {} {} products for keywords [{}].", products.size(), getStore(), reformattedKeywords);

            return new AsyncResult<>(products);
        } catch (IOException e) {
            logger.error("Unable to search for " + getStore() + " products", e);
            return new AsyncResult<>(new ArrayList<>());
        }
    }

    @Override
    public Store getStore() {
        return Coles;
    }

}
