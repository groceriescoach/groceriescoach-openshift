package com.groceriescoach.thepharmacy.domain;

import com.groceriescoach.core.domain.GroceriesCoachSortType;
import com.groceriescoach.core.domain.ProductInformationUnavailableException;
import com.groceriescoach.core.service.StoreSearchResult;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class ThePharmacySearchResult extends StoreSearchResult<ThePharmacyProduct> {

    private static final long serialVersionUID = 4097006823587132946L;

    public ThePharmacySearchResult(Document document, GroceriesCoachSortType sortType) {
        super(document, sortType);
    }

    @Override
    protected String getCssQuery() {
        return "li.item";
    }

    @Override
    protected ThePharmacyProduct fromProductElement(Element productElement, GroceriesCoachSortType sortType)
            throws ProductInformationUnavailableException {
        return new ThePharmacyProduct(productElement, sortType);
    }
}
