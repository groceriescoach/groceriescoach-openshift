package com.groceriescoach.babybunting.domain;

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

public class BabyBuntingSearchResult extends GroceriesCoachSearchResult<BabyBuntingProduct> {

    private static final long serialVersionUID = -7704062795180569959L;

    private static final Logger logger = LoggerFactory.getLogger(BabyBuntingSearchResult.class);

    public BabyBuntingSearchResult(Document document) {
        super(document);
    }

    @Override
    protected String getCssQuery() {
        return ".item";
    }

    @Override
    protected BabyBuntingProduct fromProductElement(Element productElement) {
        return BabyBuntingProduct.fromProductElement(productElement);
    }
}
