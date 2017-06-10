package com.groceriescoach.coles.domain;

import com.groceriescoach.core.domain.GroceriesCoachSortType;
import com.groceriescoach.core.domain.ProductInformationUnavailableException;
import com.groceriescoach.core.service.GroceriesCoachSearchResult;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ColesSearchResult extends GroceriesCoachSearchResult<ColesProduct> {

    private static final long serialVersionUID = -7704062795180569959L;

    private static final Logger logger = LoggerFactory.getLogger(ColesSearchResult.class);

    public ColesSearchResult(Document document, GroceriesCoachSortType sortType) {
        super(document, sortType);
    }

    @Override
    protected String getCssQuery() {
        return ".wrapper.clearfix";
    }

    @Override
    protected ColesProduct fromProductElement(Element productElement, GroceriesCoachSortType sortType) throws ProductInformationUnavailableException {
        return new ColesProduct(productElement, sortType);
    }

}
