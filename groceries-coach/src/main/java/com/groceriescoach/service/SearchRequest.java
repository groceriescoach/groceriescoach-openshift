package com.groceriescoach.service;

import com.groceriescoach.core.com.groceriescoach.core.utils.StringUtils;
import com.groceriescoach.core.domain.GroceriesCoachSortType;
import com.groceriescoach.core.domain.Store;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static com.groceriescoach.core.domain.GroceriesCoachSortType.Price;
import static com.groceriescoach.core.domain.GroceriesCoachSortType.UnitPrice;

public class SearchRequest {

    public static final String ALL_KEYWORDS_REQUIRED = "all keywords required";
    public static final String ALL_KEYWORDS_ARE_REQUIRED = "all keywords are required";
    public static final String SORT_BY = "sort by";
    public static final String ORDER_BY = "order by";


    private String keywords;
    private List<Store> stores = new ArrayList<>();
    private GroceriesCoachSortType sortType = Price;
    private boolean allKeywordsRequired;


    private static final Logger logger = LoggerFactory.getLogger(SearchRequest.class);

    public static SearchRequest createSearchRequest(
            String keywords, Collection<Store> stores, GroceriesCoachSortType sortType, boolean allKeywordsRequired) {
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.allKeywordsRequired = allKeywordsRequired;
        searchRequest.keywords = keywords;
        searchRequest.sortType = sortType;
        searchRequest.stores.addAll(stores);
        return searchRequest;
    }


    public String getKeywords() {
        return keywords;
    }

    public List<Store> getStores() {
        return new ArrayList<>(stores);
    }

    public GroceriesCoachSortType getSortType() {
        return sortType;
    }

    public boolean isAllKeywordsRequired() {
        return allKeywordsRequired;
    }

    public static SearchRequest createSearchRequest(String searchString) {

        SearchRequest searchRequest = new SearchRequest();
        searchString = StringUtils.trimToEmpty(searchString).toLowerCase();

        searchString = updateAllKeywordsRequiredFlag(searchRequest, searchString);
        searchString = updateSortBy(searchRequest, searchString);

        searchString = updateSearchKeywords(searchRequest, searchString);

        updateStores(searchRequest, searchString);

        return searchRequest;
    }

    private static String updateSearchKeywords(SearchRequest searchRequest, String searchString) {

        final String[] searchStringTokenArray = StringUtils.split(StringUtils.trimToEmpty(searchString).toLowerCase());
        final List<String> searchStringTokens = Arrays.asList(searchStringTokenArray);
        final int forIndex = searchStringTokens.indexOf("for");
        if (forIndex != -1) {
            searchRequest.keywords = StringUtils.join(searchStringTokens.subList(forIndex+1, searchStringTokens.size()), " ");
            searchString = searchString.replaceAll(searchRequest.keywords, "");
        }

        return searchString;
    }

    private static void updateStores(SearchRequest searchRequest, String searchString) {
        searchRequest.stores = Store.getStoresFrom(searchString);
    }

    private static String updateSortBy(SearchRequest searchRequest, String searchString) {
        if (StringUtils.containsIgnoreCase(searchString, SORT_BY + " unit price")) {
            searchRequest.sortType = UnitPrice;
            searchString = StringUtils.trimToEmpty(searchString.replaceAll(SORT_BY + " unit price", ""));

        } else if (StringUtils.containsIgnoreCase(searchString, ORDER_BY + " unit price")) {
            searchRequest.sortType = UnitPrice;
            searchString = StringUtils.trimToEmpty(searchString.replaceAll(ORDER_BY + " unit price", ""));
        }

        return searchString;
    }

    private static String updateAllKeywordsRequiredFlag(SearchRequest searchRequest, String searchString) {
        if (searchString.contains(ALL_KEYWORDS_REQUIRED)) {
            searchString = StringUtils.trimToEmpty(searchString.replaceAll(ALL_KEYWORDS_REQUIRED, ""));
            searchRequest.allKeywordsRequired = true;
        } else if (searchString.contains(ALL_KEYWORDS_ARE_REQUIRED)) {
            searchString = StringUtils.trimToEmpty(searchString.replaceAll(ALL_KEYWORDS_ARE_REQUIRED, ""));
            searchRequest.allKeywordsRequired = true;
        }
        return searchString;
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("keywords", keywords)
                .append("stores", stores)
                .append("sortType", sortType)
                .append("allKeywordsRequired", allKeywordsRequired)
                .toString();
    }
}
