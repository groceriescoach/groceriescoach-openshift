package com.groceriescoach.fruitezy.domain;

import com.groceriescoach.core.domain.GroceriesCoachSortType;
import com.groceriescoach.core.domain.ProductInformationUnavailableException;
import com.groceriescoach.core.service.StoreSearchResult;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class FruitEzySearchResult extends StoreSearchResult<FruitEzyProduct> {

    private static final long serialVersionUID = -6787492503250079451L;

    public FruitEzySearchResult(Document document, GroceriesCoachSortType sortType) {
        super(document, sortType);
    }

    @Override
    protected String getCssQuery() {
        return ".product";
    }

    @Override
    protected FruitEzyProduct fromProductElement(Element productElement, GroceriesCoachSortType sortType)
            throws ProductInformationUnavailableException {

        return new FruitEzyProduct(productElement, sortType);
    }
}
