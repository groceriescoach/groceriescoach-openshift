package com.groceriescoach.pharmacy4less.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Result implements Serializable {

    private static final long serialVersionUID = 8636134656224979538L;
    @JsonProperty("hits")
    private List<Hit> hits;


    public static List<Pharmacy4LessProduct> toGroceriesCoachProducts(List<Result> results) {
        List<Pharmacy4LessProduct> products = new ArrayList<>();
        for (Result result : results) {
            products.addAll(result.toGroceriesCoachProducts());
        }
        return products;
    }

    private Collection<Pharmacy4LessProduct> toGroceriesCoachProducts() {
        return Hit.toGroceriesCoachProducts(hits);
    }
}
