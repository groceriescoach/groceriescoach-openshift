package com.groceriescoach.target.domain;

import com.groceriescoach.core.service.GroceriesCoachSearchResult;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TargetSearchResult extends GroceriesCoachSearchResult<TargetProduct> {

    private static final long serialVersionUID = -7704062795180569959L;

    private static final Logger logger = LoggerFactory.getLogger(TargetSearchResult.class);

    public TargetSearchResult(Document document) {
        super(document);
    }

    @Override
    protected String getCssQuery() {
        return null;
    }

    @Override
    protected TargetProduct fromProductElement(Element productElement) {
        return null;
    }
}
