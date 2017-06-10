package com.groceriescoach.priceline.domain;


import com.groceriescoach.core.domain.GroceriesCoachSortType;
import com.groceriescoach.core.domain.ProductInformationUnavailableException;
import com.groceriescoach.core.service.GroceriesCoachSearchResult;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PricelineSearchResult extends GroceriesCoachSearchResult<PricelineProduct> {

    private static final long serialVersionUID = 2751225604575303422L;

    private static final Logger logger = LoggerFactory.getLogger(PricelineSearchResult.class);

    public PricelineSearchResult(Document document, GroceriesCoachSortType sortType) {
        super(document, sortType);
    }

    @Override
    protected String getCssQuery() {
        return ".item";
    }

    @Override
    protected PricelineProduct fromProductElement(Element productElement, GroceriesCoachSortType sortType) throws ProductInformationUnavailableException {
        return new PricelineProduct(productElement, sortType);
    }

}
