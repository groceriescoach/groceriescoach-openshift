package com.groceriescoach.mrvitamins.domain;

import com.groceriescoach.core.domain.GroceriesCoachSortType;
import com.groceriescoach.core.domain.ProductInformationUnavailableException;
import com.groceriescoach.core.service.GroceriesCoachSearchResult;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class MrVitaminsSearchResult extends GroceriesCoachSearchResult<MrVitaminsProduct> {

    public MrVitaminsSearchResult(Document document, GroceriesCoachSortType sortType) {
        super(document, sortType);
    }

    @Override
    protected String getCssQuery() {
        return ".snize-product";
    }

    @Override
    protected MrVitaminsProduct fromProductElement(Element productElement, GroceriesCoachSortType sortType) throws ProductInformationUnavailableException {
        return new MrVitaminsProduct(productElement, sortType);
    }
}
