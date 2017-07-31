package com.groceriescoach.pharmacy4less.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.groceriescoach.core.domain.GroceriesCoachSortType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Pharmacy4LessSearchResult  {

    private static final long serialVersionUID = -7704062795180569959L;

    @JsonProperty("results")
    private List<Result> results;

    private static final Logger logger = LoggerFactory.getLogger(Pharmacy4LessSearchResult.class);



    public List<Pharmacy4LessProduct> toProducts(GroceriesCoachSortType sortType) {
        return Result.toGroceriesCoachProducts(results, sortType);
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }
}
