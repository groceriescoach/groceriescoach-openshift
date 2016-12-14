package com.groceriescoach.priceline.domain;


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

public class PricelineSearchResult implements Serializable{


    private static final long serialVersionUID = 2751225604575303422L;

    private List<PricelineProduct> pricelineProducts;


    private static final Logger logger = LoggerFactory.getLogger(PricelineSearchResult.class);

    public PricelineSearchResult(Document document) {

        pricelineProducts = new ArrayList<>();

        Elements searchResults = document.select(".item.type-simple");

        for (Element productElement : searchResults) {
            try {
                PricelineProduct pricelineProduct = PricelineProduct.fromProductElement(productElement);
                pricelineProducts.add(pricelineProduct);
            } catch (Exception e) {
                logger.error("Unable to extract product from: " + productElement, e);
            }
        }
    }


    public List<PricelineProduct> getPricelineProducts() {
        return pricelineProducts;
    }

    public void setPricelineProducts(List<PricelineProduct> pricelineProducts) {
        this.pricelineProducts = pricelineProducts;
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("pricelineProducts", pricelineProducts)
                .toString();
    }

    public List<Product> toProducts() {

        List<Product> products = new ArrayList<>();

        for (PricelineProduct pricelineProduct : pricelineProducts) {
            products.add(pricelineProduct.toGroceriesCoachProduct());
        }

        return products;
    }


}
