package com.groceriescoach.cincottachemist.domain;

import com.groceriescoach.core.domain.GroceriesCoachSortType;
import com.groceriescoach.core.domain.ProductInformationUnavailableException;
import com.groceriescoach.core.service.StoreSearchResult;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class CincottaChemistSearchResult extends StoreSearchResult<CincottaChemistProduct> {

    private static final long serialVersionUID = -6787492503250079451L;

    public CincottaChemistSearchResult(Document document, GroceriesCoachSortType sortType) {
        super(document, sortType);
    }

    @Override
    protected String getCssQuery() {
        return ".item";
    }

    @Override
    protected CincottaChemistProduct fromProductElement(Element productElement, GroceriesCoachSortType sortType)
            throws ProductInformationUnavailableException {

        return new CincottaChemistProduct(productElement, sortType);
    }
}
