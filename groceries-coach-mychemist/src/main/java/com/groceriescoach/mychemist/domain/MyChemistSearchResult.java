package com.groceriescoach.mychemist.domain;

import com.groceriescoach.core.domain.GroceriesCoachSortType;
import com.groceriescoach.core.domain.ProductInformationUnavailableException;
import com.groceriescoach.core.service.StoreSearchResult;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class MyChemistSearchResult extends StoreSearchResult<MyChemistProduct> {

    private static final long serialVersionUID = -826512369772606214L;

    public MyChemistSearchResult(Document document, GroceriesCoachSortType sortType) {
        super(document, sortType);
    }

    @Override
    protected String getCssQuery() {
        return ".product_tile_row .column";
    }

    @Override
    protected MyChemistProduct fromProductElement(Element productElement, GroceriesCoachSortType sortType) throws ProductInformationUnavailableException {
        return new MyChemistProduct(productElement, sortType);
    }
}
