package com.groceriescoach.terrywhite.domain;

import com.groceriescoach.core.domain.GroceriesCoachProduct;
import com.groceriescoach.core.domain.GroceriesCoachSortType;
import com.groceriescoach.core.domain.ProductInformationUnavailableException;
import com.groceriescoach.core.service.StoreSearchResult;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TerryWhiteSearchResult extends StoreSearchResult {

    private static final long serialVersionUID = -7704062795180569959L;

    private static final Logger logger = LoggerFactory.getLogger(TerryWhiteSearchResult.class);

    public TerryWhiteSearchResult(Document document, GroceriesCoachSortType sortType) {
        super(document, sortType);
    }

    @Override
    protected String getCssQuery() {
        return ".category-item";
    }

    @Override
    protected GroceriesCoachProduct fromProductElement(Element productElement, GroceriesCoachSortType sortType)
            throws ProductInformationUnavailableException {
        return new TerryWhiteProduct(productElement, sortType);
    }

}
