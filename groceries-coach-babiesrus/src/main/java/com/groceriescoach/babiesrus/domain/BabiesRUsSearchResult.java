package com.groceriescoach.babiesrus.domain;

import com.groceriescoach.core.service.GroceriesCoachSearchResult;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BabiesRUsSearchResult extends GroceriesCoachSearchResult<BabiesRUsProduct> {

    private static final long serialVersionUID = -7704062795180569959L;

    private static final Logger logger = LoggerFactory.getLogger(BabiesRUsSearchResult.class);

    public BabiesRUsSearchResult(Document document) {
        super(document);
    }

    @Override
    protected String getCssQuery() {
        return ".PFProductContainer";
    }

    @Override
    protected BabiesRUsProduct fromProductElement(Element productElement) {
        return BabiesRUsProduct.fromProductElement(productElement);
    }
}
