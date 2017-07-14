package com.groceriescoach.service;

import org.junit.Test;

import static com.groceriescoach.core.domain.GroceriesCoachSortType.Price;
import static com.groceriescoach.core.domain.GroceriesCoachSortType.UnitPrice;
import static com.groceriescoach.core.domain.Store.Coles;
import static com.groceriescoach.core.domain.Store.Woolworths;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class SearchRequestTest {


    @Test
    public void allKeywordsRequired() {
        String searchString = "Search Woolworths and Coles for Babylove nappies all keywords required";
        SearchRequest searchRequest = SearchRequest.createSearchRequest(searchString);
        assertThat(searchRequest.isAllKeywordsRequired(), is(true));
    }

    @Test
    public void allKeywordsAreRequired() {
        String searchString = "Search Woolworths and Coles for Babylove nappies all keywords are required";
        SearchRequest searchRequest = SearchRequest.createSearchRequest(searchString);
        assertThat(searchRequest.isAllKeywordsRequired(), is(true));
    }

    @Test
    public void allKeywordsAreNotRequired() {
        String searchString = "Search Woolworths and Coles for Babylove nappies";
        SearchRequest searchRequest = SearchRequest.createSearchRequest(searchString);
        assertThat(searchRequest.isAllKeywordsRequired(), is(false));
    }

    @Test
    public void sortByPriceIsTheDefaultOption() {
        String searchString = "Search Woolworths and Coles for Babylove nappies";
        SearchRequest searchRequest = SearchRequest.createSearchRequest(searchString);
        assertThat(searchRequest.getSortType(), is(Price));
    }

    @Test
    public void sortByPrice() {
        String searchString = "Search Woolworths and Coles for Babylove nappies sort by price";
        SearchRequest searchRequest = SearchRequest.createSearchRequest(searchString);
        assertThat(searchRequest.getSortType(), is(Price));
    }

    @Test
    public void orderByPrice() {
        String searchString = "Search Woolworths and Coles for Babylove nappies order by price";
        SearchRequest searchRequest = SearchRequest.createSearchRequest(searchString);
        assertThat(searchRequest.getSortType(), is(Price));
    }
    @Test
    public void sortByUnitPrice() {
        String searchString = "Search Woolworths and Coles for Babylove nappies sort by unit price";
        SearchRequest searchRequest = SearchRequest.createSearchRequest(searchString);
        assertThat(searchRequest.getSortType(), is(UnitPrice));
    }

    @Test
    public void orderByUnitPrice() {
        String searchString = "Search Woolworths and Coles for Babylove nappies order by unit price";
        SearchRequest searchRequest = SearchRequest.createSearchRequest(searchString);
        assertThat(searchRequest.getSortType(), is(UnitPrice));
    }

    @Test
    public void searchForBabyloveNappies() {
        String searchString = "Search Woolworths and Coles for Babylove nappies order by unit price";
        SearchRequest searchRequest = SearchRequest.createSearchRequest(searchString);
        assertThat(searchRequest.getKeywords(), is("babylove nappies"));
    }
    
    @Test
    public void searchWoolworthsAndColes() {
        String searchString = "Search Woolworths and Coles for Babylove nappies order by unit price";
        SearchRequest searchRequest = SearchRequest.createSearchRequest(searchString);
        assertThat(searchRequest.getStores(), containsInAnyOrder(equalTo(Woolworths), equalTo(Coles)));
    }

}
