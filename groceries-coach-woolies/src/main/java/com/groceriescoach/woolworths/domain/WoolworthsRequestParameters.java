package com.groceriescoach.woolworths.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class WoolworthsRequestParameters implements Serializable {

    private static final long serialVersionUID = 1556791159962396214L;

    @JsonProperty("SearchTerm")
    private String keywords;

    @JsonProperty("PageSize")
    private final int pageSize = 36;

    @JsonProperty("PageNumber")
    private int page;

    @JsonProperty("SortType")
    private final String sortType = "TraderRelevance";


    public static WoolworthsRequestParameters createParameters(String keywords, int page) {
        WoolworthsRequestParameters requestParameters = new WoolworthsRequestParameters();
        requestParameters.keywords = keywords;
        requestParameters.page = page;
        return requestParameters;
    }
}
