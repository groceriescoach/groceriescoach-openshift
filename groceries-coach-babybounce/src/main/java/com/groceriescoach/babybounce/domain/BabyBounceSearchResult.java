package com.groceriescoach.babybounce.domain;

import com.groceriescoach.core.domain.GroceriesCoachSortType;
import com.groceriescoach.core.domain.ProductInformationUnavailableException;
import com.groceriescoach.core.service.StoreSearchResult;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

public class BabyBounceSearchResult extends StoreSearchResult<BabyBounceProduct> implements Serializable {

    private static final long serialVersionUID = -7704062795180569959L;


    private static final Logger logger = LoggerFactory.getLogger(BabyBounceSearchResult.class);

    public BabyBounceSearchResult(Document document, GroceriesCoachSortType sortType) {
        super(document, sortType);
    }

    @Override
    protected String getCssQuery() {
        return ".prd";
    }

    @Override
    protected BabyBounceProduct fromProductElement(Element productElement, GroceriesCoachSortType sortType) throws ProductInformationUnavailableException {
        return new BabyBounceProduct(productElement, sortType);
    }

}
