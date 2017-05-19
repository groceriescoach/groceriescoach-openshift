package com.groceriescoach.kmart.domain;

import com.groceriescoach.core.service.GroceriesCoachSearchResult;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KmartSearchResult extends GroceriesCoachSearchResult<KmartProduct> {

    private static final long serialVersionUID = -7704062795180569959L;

    private static final Logger logger = LoggerFactory.getLogger(KmartSearchResult.class);

    public KmartSearchResult(Document document) {
        super(document);
    }

    @Override
    protected String getCssQuery() {
        return null;
    }

    @Override
    protected KmartProduct fromProductElement(Element productElement) {
        return null;
    }
}
