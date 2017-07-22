package com.groceriescoach.pharmacy4less.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrencyCost {

    @JsonProperty("default")
    private Double defaultCost;

    @JsonProperty("default_formatted")
    private String defaultFormatted;

    public Double getDefaultCost() {
        return defaultCost;
    }

    public void setDefaultCost(Double defaultCost) {
        this.defaultCost = defaultCost;
    }

    public String getDefaultFormatted() {
        return defaultFormatted;
    }

    public void setDefaultFormatted(String defaultFormatted) {
        this.defaultFormatted = defaultFormatted;
    }
}
