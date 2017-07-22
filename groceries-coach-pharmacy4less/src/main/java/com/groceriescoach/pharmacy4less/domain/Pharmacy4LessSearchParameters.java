package com.groceriescoach.pharmacy4less.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static com.groceriescoach.pharmacy4less.domain.Pharmacy4LessSearchParameters.Request.createRequest;

public class Pharmacy4LessSearchParameters implements Serializable {

    private static final long serialVersionUID = -8841391149089366779L;

    private List<Request> requests = new ArrayList<>();


    public static Pharmacy4LessSearchParameters createParameters(String keywords) {
        Pharmacy4LessSearchParameters searchParameters = new Pharmacy4LessSearchParameters();
        searchParameters.requests.add(createRequest(keywords));
        return searchParameters;
    }

// {"requests":[{"indexName":"pharmacy4less_pharmacy4less_products","params":"query=perfume&hitsPerPage=90&maxValuesPerFacet=10&page=0&facets=%5B%22activation_information%22%2C%22manufacturer%22%2C%22price.AUD.default%22%2C%22categories.level0%22%5D&tagFilters="}]}


    public List<Request> getRequests() {
        return requests;
    }

    static final class Request {

        private final String indexName = "pharmacy4less_pharmacy4less_products";
        private String params;

        static Request createRequest(String keywords) {
            Request request = new Request();
            request.params = "query=" + keywords + "&hitsPerPage=15&maxValuesPerFacet=10&page=0&facets=%5B%22activation_information%22%2C%22manufacturer%22%2C%22price.AUD.default%22%2C%22categories.level0%22%5D&tagFilters=";
            return request;
        }

        public String getIndexName() {
            return indexName;
        }

        public String getParams() {
            return params;
        }
    }

}
