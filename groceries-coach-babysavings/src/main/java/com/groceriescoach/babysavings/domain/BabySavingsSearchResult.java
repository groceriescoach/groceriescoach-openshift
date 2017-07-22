package com.groceriescoach.babysavings.domain;

import com.groceriescoach.core.domain.GroceriesCoachSortType;
import com.groceriescoach.core.domain.ProductInformationUnavailableException;
import com.groceriescoach.core.service.StoreSearchResult;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class BabySavingsSearchResult extends StoreSearchResult<BabySavingsProduct> {

    private static final long serialVersionUID = 5074851770655160838L;

    public BabySavingsSearchResult(Document document, GroceriesCoachSortType sortType) {
        super(document, sortType);
    }

    @Override
    protected String getCssQuery() {
        return ".product_wraper";
    }

    @Override
    protected BabySavingsProduct fromProductElement(Element productElement, GroceriesCoachSortType sortType)
            throws ProductInformationUnavailableException {
        return new BabySavingsProduct(productElement, sortType);
    }
}
