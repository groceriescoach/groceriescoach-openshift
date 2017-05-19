package com.groceriescoach.babyandtoddlertown.domain;

import com.groceriescoach.core.service.GroceriesCoachSearchResult;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BabyAndToddlerTownSearchResult extends GroceriesCoachSearchResult<BabyAndToddlerTownProduct> {

    private static final long serialVersionUID = -7704062795180569959L;

    private static final Logger logger = LoggerFactory.getLogger(BabyAndToddlerTownSearchResult.class);

    public BabyAndToddlerTownSearchResult(Document document) {
        super(document);
    }

    @Override
    protected String getCssQuery() {
        return ".item";
    }

    @Override
    protected BabyAndToddlerTownProduct fromProductElement(Element productElement) {
        return BabyAndToddlerTownProduct.fromProductElement(productElement);
    }
}
