package com.groceriescoach.nursingangel.domain;

import com.groceriescoach.core.service.GroceriesCoachSearchResult;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NursingAngelSearchResult extends GroceriesCoachSearchResult<NursingAngelProduct> {

    private static final long serialVersionUID = -7704062795180569959L;

    private static final Logger logger = LoggerFactory.getLogger(NursingAngelSearchResult.class);

    public NursingAngelSearchResult(Document document) {
        super(document);
    }

    @Override
    protected String getCssQuery() {
        return ".wrapper-thumbnail";
    }

    @Override
    protected NursingAngelProduct fromProductElement(Element productElement) {
        return NursingAngelProduct.fromProductElement(productElement);
    }
}
