package com.groceriescoach.babyvillage.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class BabyVillageSearchResult {

    private static final long serialVersionUID = -7704062795180569959L;

    @JsonProperty("totalResults")
    private int totalResults;

    @JsonProperty("page")
    private int page;

    @JsonProperty("pageSize")
    private int pageSize;

    @JsonProperty("keyWord")
    private String keyword;

    @JsonProperty("results")
    private String[] results;

    @JsonProperty("dataLayer")
    private String dataLayer;


    private static final Logger logger = LoggerFactory.getLogger(BabyVillageSearchResult.class);


    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String[] getResults() {
        return results;
    }

    public void setResults(String[] results) {
        this.results = results;
    }

    public String getDataLayer() {
        return dataLayer;
    }

    public void setDataLayer(String dataLayer) {
        this.dataLayer = dataLayer;
    }

    public List<BabyVillageProduct> toProducts() {

        List<BabyVillageProduct> products = new ArrayList<>();

        for (String result : results) {
            Document productElement = Jsoup.parse(result);
            try {
                products.add(BabyVillageProduct.fromProductElement(productElement));
            } catch (Exception e) {
                logger.error("Unable to extract product from: " + productElement, e);
            }
        }
        return products;
    }
}
