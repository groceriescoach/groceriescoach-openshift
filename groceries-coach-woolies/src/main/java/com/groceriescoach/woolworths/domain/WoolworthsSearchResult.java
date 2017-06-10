package com.groceriescoach.woolworths.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.List;

public class WoolworthsSearchResult implements Serializable {

    private static final long serialVersionUID = -7704062795180569959L;

    @JsonProperty("Products")
    private List<Product> products;

    @JsonProperty("Corrections")
    private List<Correction> corrections;

    @JsonProperty("SearchResultsCount")
    private int searchResultsCount;

    @JsonProperty("VisualShoppingAisleResponse")
    private VisualShoppingAisleResponse visualShoppingAisleResponse[];

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public List<Correction> getCorrections() {
        return corrections;
    }

    public void setCorrections(List<Correction> corrections) {
        this.corrections = corrections;
    }

    public int getSearchResultsCount() {
        return searchResultsCount;
    }

    public void setSearchResultsCount(int searchResultsCount) {
        this.searchResultsCount = searchResultsCount;
    }

    public VisualShoppingAisleResponse[] getVisualShoppingAisleResponse() {
        return visualShoppingAisleResponse;
    }

    public void setVisualShoppingAisleResponse(VisualShoppingAisleResponse[] visualShoppingAisleResponse) {
        this.visualShoppingAisleResponse = visualShoppingAisleResponse;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("products", products)
                .append("corrections", corrections)
                .append("searchResultsCount", searchResultsCount)
                .append("visualShoppingAisleResponse", visualShoppingAisleResponse)
                .toString();
    }

    public List<com.groceriescoach.core.domain.Product> toProducts(String keywords) {
        return Product.toProducts(products, keywords);
    }

}
