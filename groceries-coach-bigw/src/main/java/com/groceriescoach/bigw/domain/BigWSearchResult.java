package com.groceriescoach.bigw.domain;

import com.groceriescoach.core.service.GroceriesCoachSearchResult;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BigWSearchResult extends GroceriesCoachSearchResult<BigWProduct> {

    private static final long serialVersionUID = -7704062795180569959L;

    private static final Logger logger = LoggerFactory.getLogger(BigWSearchResult.class);

    public BigWSearchResult(Document document) {
        super(document);
    }

    @Override
    protected String getCssQuery() {
        return ".productGridItem";
    }

    @Override
    protected BigWProduct fromProductElement(Element productElement) {
        return BigWProduct.fromProductElement(productElement);
    }
}
