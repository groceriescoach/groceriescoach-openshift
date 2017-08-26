package com.groceriescoach.woolworths.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ChildProduct {

    @JsonProperty("Product")
    private ProductDetails productDetails;


}
