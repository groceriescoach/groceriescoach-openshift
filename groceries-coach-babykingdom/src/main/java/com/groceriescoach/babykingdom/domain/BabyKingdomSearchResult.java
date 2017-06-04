package com.groceriescoach.babykingdom.domain;

import com.groceriescoach.core.service.GroceriesCoachSearchResult;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BabyKingdomSearchResult extends GroceriesCoachSearchResult<BabyKingdomProduct> {

    private static final long serialVersionUID = -7704062795180569959L;

    private static final Logger logger = LoggerFactory.getLogger(BabyKingdomSearchResult.class);

    public BabyKingdomSearchResult(Document document) {
        super(document);
    }

    @Override
    protected String getCssQuery() {
        return ".prodformat_box";
    }

    @Override
    protected BabyKingdomProduct fromProductElement(Element productElement) {
        return BabyKingdomProduct.fromProductElement(productElement);
    }
}
