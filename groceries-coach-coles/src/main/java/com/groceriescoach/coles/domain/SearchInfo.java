package com.groceriescoach.coles.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonIgnoreProperties({"params", "suggestedterms", "manufacturer", "profileName", "searchSource", "intentSearchTerm", "originalSearchTerm", "metaData", "currency", "filterTerm", "filterType", "filterFacet", "maxPrice", "minPrice", "orderBy", "physicalStoreIds", "advancedFacetList", "pageView", "personaliseSearch","personaliseSort", "responseTemplate"})
public class SearchInfo implements Serializable {
    private static final long serialVersionUID = -719440534846439925L;

    @JsonProperty("pageSize")
    private int pageSize;

    @JsonProperty("searchTerm")
    private String searchTerm;

    @JsonProperty("categoryId")
    private String categoryId;
    @JsonProperty("searchType")
    private String searchType;

    @JsonProperty("currentPage")
    private int currentPage;
    @JsonProperty("totalCount")
    private int totalCount;

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getSearchTerm() {
        return searchTerm;
    }

    public void setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getSearchType() {
        return searchType;
    }

    public void setSearchType(String searchType) {
        this.searchType = searchType;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }


    /*
                "manufacturer": "",
                "profileName": "COLRS_findProductsBySearchTerm",
                "searchSource": "",
                "intentSearchTerm": "dilmah",
                "originalSearchTerm": "dilmah",
                "metaData": "",
                "currency": "AUD",
                "filterTerm": "",
                "filterType": "",
                "filterFacet": "",
                "maxPrice": "",
                "minPrice": "",
                "orderBy": "",
                "physicalStoreIds": "",
                "advancedFacetList": "",
                "pageView": "grid",
                "personaliseSearch": "false",
                "personaliseSort": "false",
                "responseTemplate": "",
*/




}
