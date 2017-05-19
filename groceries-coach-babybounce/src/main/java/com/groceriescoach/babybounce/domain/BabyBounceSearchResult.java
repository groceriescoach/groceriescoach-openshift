package com.groceriescoach.babybounce.domain;

import com.groceriescoach.core.domain.Product;
import com.groceriescoach.core.service.GroceriesCoachSearchResult;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BabyBounceSearchResult extends GroceriesCoachSearchResult<BabyBounceProduct> implements Serializable {

    private static final long serialVersionUID = -7704062795180569959L;


    private static final Logger logger = LoggerFactory.getLogger(BabyBounceSearchResult.class);

    public BabyBounceSearchResult(Document document) {
        super(document);
    }


    @Override
    protected String getCssQuery() {
        return ".prd";
    }

    @Override
    protected BabyBounceProduct fromProductElement(Element productElement) {
        return BabyBounceProduct.fromProductElement(productElement);
    }


}
