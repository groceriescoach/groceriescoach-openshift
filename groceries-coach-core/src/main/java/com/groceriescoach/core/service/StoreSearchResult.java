package com.groceriescoach.core.service;

import com.groceriescoach.core.domain.GroceriesCoachProduct;
import com.groceriescoach.core.domain.GroceriesCoachSortType;
import com.groceriescoach.core.domain.ProductInformationUnavailableException;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class StoreSearchResult<P extends GroceriesCoachProduct> implements Serializable {

    private static final long serialVersionUID = -7704062795180569959L;

    protected List<P> products;

    private static final Logger logger = LoggerFactory.getLogger(StoreSearchResult.class);

    protected StoreSearchResult() {

    }

    public StoreSearchResult(Document document, GroceriesCoachSortType sortType) {

        products = new ArrayList<>();

        Elements searchResults = document.select(getCssQuery());

        for (Element productElement : searchResults) {
            try {
                P product = fromProductElement(productElement, sortType);
                products.add(product);
            } catch (ProductInformationUnavailableException e) {
                logger.error("Unable to extract product from: " + productElement);
            } catch (Exception e) {
                logger.error("Unable to extract product from: " + productElement, e);
            }
        }
    }


    protected abstract String getCssQuery();

    protected abstract P fromProductElement(Element productElement, GroceriesCoachSortType sortType) throws ProductInformationUnavailableException;


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
