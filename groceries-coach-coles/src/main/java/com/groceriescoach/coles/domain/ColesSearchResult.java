package com.groceriescoach.coles.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.groceriescoach.core.domain.GroceriesCoachSortType;

import java.io.Serializable;
import java.util.Collection;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ColesSearchResult implements Serializable {

    private static final long serialVersionUID = 183586110809907116L;

    @JsonProperty("type")
    private String type;
    @JsonProperty("products")
    private Product[] products;
    @JsonProperty("searchInfo")
    private SearchInfo searchInfo;


    public Collection<ColesProduct> toColesProducts(GroceriesCoachSortType sortType) {
        return Product.toColesProducts(products, sortType);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Product[] getProducts() {
        return products;
    }

    public void setProducts(Product[] products) {
        this.products = products;
    }

    public SearchInfo getSearchInfo() {
        return searchInfo;
    }

    public void setSearchInfo(SearchInfo searchInfo) {
        this.searchInfo = searchInfo;
    }
}
