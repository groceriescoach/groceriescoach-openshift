package com.groceriescoach.core.service;

import com.groceriescoach.core.domain.Product;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class GroceriesCoachSearchResult<P extends Product> implements Serializable {

    private static final long serialVersionUID = -7704062795180569959L;

    private List<P> products;

    private static final Logger logger = LoggerFactory.getLogger(GroceriesCoachSearchResult.class);

    public GroceriesCoachSearchResult(Document document) {

        products = new ArrayList<>();

        Elements searchResults = document.select(getCssQuery());

        for (Element productElement : searchResults) {
            try {
                P product = fromProductElement(productElement);
                if (product != null) {
                    products.add(product);
                }
            } catch (Exception e) {
                logger.error("Unable to extract product from: " + productElement, e);
            }
        }
    }


    protected abstract String getCssQuery();

    protected abstract P fromProductElement(Element productElement);


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("products", products)
                .toString();
    }

    public List<P> getProducts() {
        return products;
    }
}
