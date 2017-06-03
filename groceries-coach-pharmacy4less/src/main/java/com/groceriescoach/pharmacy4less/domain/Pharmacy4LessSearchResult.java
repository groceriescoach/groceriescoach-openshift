package com.groceriescoach.pharmacy4less.domain;

import com.groceriescoach.core.service.GroceriesCoachSearchResult;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Pharmacy4LessSearchResult extends GroceriesCoachSearchResult<Pharmacy4LessProduct> {

    private static final long serialVersionUID = -7704062795180569959L;

    private static final Logger logger = LoggerFactory.getLogger(Pharmacy4LessSearchResult.class);

    public Pharmacy4LessSearchResult(Document document) {
        super(document);
    }

    @Override
    protected String getCssQuery() {
        return ".item";
    }

    @Override
    protected Pharmacy4LessProduct fromProductElement(Element productElement) {
        return Pharmacy4LessProduct.fromProductElement(productElement);
    }
}
