package com.groceriescoach.core.domain;

public class SearchCriteria {

    private boolean allKeywordsRequired;
    private String keywords;
    private GroceriesCoachSortType sortBy;
    private String[] stores;
    private String searchPhrase;


    public boolean isAllKeywordsRequired() {
        return allKeywordsRequired;
    }

    public void setAllKeywordsRequired(boolean allKeywordsRequired) {
        this.allKeywordsRequired = allKeywordsRequired;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public GroceriesCoachSortType getSortBy() {
        return sortBy;
    }

    public void setSortBy(GroceriesCoachSortType sortBy) {
        this.sortBy = sortBy;
    }

    public String[] getStores() {
        return stores;
    }

    public void setStores(String[] stores) {
        this.stores = stores;
    }

    public String getSearchPhrase() {
        return searchPhrase;
    }

    public void setSearchPhrase(String searchPhrase) {
        this.searchPhrase = searchPhrase;
    }
}
