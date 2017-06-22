package com.groceriescoach.babyandtoddlertown.domain;

import com.groceriescoach.core.domain.GroceriesCoachSortType;
import com.groceriescoach.core.domain.ProductInformationUnavailableException;
import com.groceriescoach.core.service.StoreSearchResult;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BabyAndToddlerTownSearchResult extends StoreSearchResult<BabyAndToddlerTownProduct> {

    private static final long serialVersionUID = -7704062795180569959L;

    private static final Logger logger = LoggerFactory.getLogger(BabyAndToddlerTownSearchResult.class);

    public BabyAndToddlerTownSearchResult(Document document, GroceriesCoachSortType sortType) {
        super(document, sortType);
    }

    @Override
    protected String getCssQuery() {
        return ".grid-item";
    }

    @Override
    protected BabyAndToddlerTownProduct fromProductElement(Element productElement, GroceriesCoachSortType sortType) throws ProductInformationUnavailableException {
        return new BabyAndToddlerTownProduct(productElement, sortType);
    }
}
