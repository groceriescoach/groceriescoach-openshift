package com.groceriescoach.service;

import com.groceriescoach.core.com.groceriescoach.core.utils.CollectionUtils;
import com.groceriescoach.core.com.groceriescoach.core.utils.StringUtils;
import com.groceriescoach.core.domain.GroceriesCoachSortType;
import com.groceriescoach.core.domain.Store;
import com.groceriescoach.core.domain.StoreType;
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
    public static final String ALL_KEYWORDS_OPTIONAL = "all keywords optional";
    public static final String ALL_KEYWORDS_ARE_REQUIRED = "all keywords are required";
    public static final String ALL_KEYWORDS_ARE_OPTIONAL = "all keywords are optional";
    public static final String SORT_BY = "sort by";
    public static final String ORDER_BY = "order by";


    private String keywords;
    private List<Store> stores = new ArrayList<>();
    private GroceriesCoachSortType sortType = Price;
    private boolean allKeywordsRequired;
    private String searchPhrase;
    private int page;

    private static final Logger logger = LoggerFactory.getLogger(SearchRequest.class);

    public static SearchRequest createSearchRequest(
            String keywords, Collection<Store> stores, GroceriesCoachSortType sortType, boolean allKeywordsRequired, int page) {
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.allKeywordsRequired = allKeywordsRequired;
        searchRequest.keywords = keywords;
        searchRequest.sortType = sortType;
        searchRequest.stores.addAll(stores);
        searchRequest.page = page;
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

    public String getSearchPhrase() {
        return searchPhrase;
    }

    public int getPage() {
        return page;
    }

    public static SearchRequest createSearchRequest(String searchPhrase) {

        SearchRequest searchRequest = new SearchRequest();
        searchRequest.searchPhrase = searchPhrase;
        searchPhrase = StringUtils.trimToEmpty(searchPhrase).toLowerCase();

        searchPhrase = updateAllKeywordsRequiredFlag(searchRequest, searchPhrase);
        searchPhrase = updateSortBy(searchRequest, searchPhrase);

        searchPhrase = updateSearchKeywords(searchRequest, searchPhrase);

        updateStores(searchRequest, searchPhrase);

        searchRequest.page = 1;

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
        searchRequest.stores = StoreType.getStoresFrom(searchString);
        searchRequest.stores.addAll(Store.getStoresFrom(searchString));
        if (CollectionUtils.isEmpty(searchRequest.stores)) {
            logger.debug("No stores specified in search phrase, will search all stores.");
            searchRequest.stores.addAll(Store.getAllStores());
        }
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
        } else if (searchString.contains(ALL_KEYWORDS_ARE_OPTIONAL)) {
            searchString = StringUtils.trimToEmpty(searchString.replaceAll(ALL_KEYWORDS_ARE_OPTIONAL, ""));
            searchRequest.allKeywordsRequired = false;
        } else if (searchString.contains(ALL_KEYWORDS_OPTIONAL)) {
            searchString = StringUtils.trimToEmpty(searchString.replaceAll(ALL_KEYWORDS_OPTIONAL, ""));
            searchRequest.allKeywordsRequired = false;
        } else {
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
                .append("page", page)
                .toString();
    }
}
