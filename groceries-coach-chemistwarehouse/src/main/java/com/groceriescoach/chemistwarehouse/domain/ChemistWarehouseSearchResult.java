package com.groceriescoach.chemistwarehouse.domain;

import com.groceriescoach.core.domain.GroceriesCoachSortType;
import com.groceriescoach.core.domain.ProductInformationUnavailableException;
import com.groceriescoach.core.service.GroceriesCoachSearchResult;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChemistWarehouseSearchResult extends GroceriesCoachSearchResult<ChemistWarehouseProduct> {

    private static final long serialVersionUID = -7704062795180569959L;

    private static final Logger logger = LoggerFactory.getLogger(ChemistWarehouseSearchResult.class);

    public ChemistWarehouseSearchResult(Document document, GroceriesCoachSortType sortType) {
        super(document, sortType);
    }

    @Override
    protected String getCssQuery() {
        return ".product-container";
    }

    @Override
    protected ChemistWarehouseProduct fromProductElement(Element productElement, GroceriesCoachSortType sortType) throws ProductInformationUnavailableException {
        return new ChemistWarehouseProduct(productElement, sortType);
    }


}
