package com.groceriescoach.pharmacydirect.domain;

import com.groceriescoach.core.domain.GroceriesCoachSortType;
import com.groceriescoach.core.domain.ProductInformationUnavailableException;
import com.groceriescoach.core.service.StoreSearchResult;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class PharmacyDirectSearchResult extends StoreSearchResult<PharmacyDirectProduct> {
    private static final long serialVersionUID = 1294001459444403288L;

    public PharmacyDirectSearchResult(Document document, GroceriesCoachSortType sortType) {
        super(document, sortType);
    }

    @Override
    protected String getCssQuery() {
        return ".sli_grid_result";
    }

    @Override
    protected PharmacyDirectProduct fromProductElement(Element productElement, GroceriesCoachSortType sortType)
            throws ProductInformationUnavailableException {
        return new PharmacyDirectProduct(productElement, sortType);
    }
}
