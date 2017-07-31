package com.groceriescoach.pharmacy4less.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.groceriescoach.core.domain.GroceriesCoachSortType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Result implements Serializable {

    private static final long serialVersionUID = 8636134656224979538L;
    @JsonProperty("hits")
    private List<Hit> hits;


    public static List<Pharmacy4LessProduct> toGroceriesCoachProducts(List<Result> results, GroceriesCoachSortType sortType) {
        List<Pharmacy4LessProduct> products = new ArrayList<>();
        for (Result result : results) {
            products.addAll(result.toGroceriesCoachProducts(sortType));
        }
        return products;
    }

    private Collection<Pharmacy4LessProduct> toGroceriesCoachProducts(GroceriesCoachSortType sortType) {
        return Hit.toGroceriesCoachProducts(hits, sortType);
    }
}
