package com.groceriescoach.royyoung.domain;

import com.groceriescoach.core.domain.GroceriesCoachSortType;
import com.groceriescoach.core.domain.ProductInformationUnavailableException;
import com.groceriescoach.core.service.StoreSearchResult;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class RoyYoungSearchResult extends StoreSearchResult<RoyYoungProduct> {

    private static final long serialVersionUID = 7462052074010381053L;

    public RoyYoungSearchResult(Document document, GroceriesCoachSortType sortType) {
        super(document, sortType);
    }

    @Override
    protected String getCssQuery() {
        return ".item";
    }

    @Override
    protected RoyYoungProduct fromProductElement(Element productElement, GroceriesCoachSortType sortType)
            throws ProductInformationUnavailableException {
        return new RoyYoungProduct(productElement, sortType);
    }
}
