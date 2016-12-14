package com.groceriescoach.coles.domain;

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

public class ColesSearchResult implements Serializable {

    private static final long serialVersionUID = -7704062795180569959L;

    private List<ColesProduct> colesProducts;


    private static final Logger logger = LoggerFactory.getLogger(ColesSearchResult.class);

    public ColesSearchResult(Document document) {

        colesProducts = new ArrayList<>();

//        Elements searchResults = document.select(".outer-prod.prodtile .wrapper.clearfix");
        Elements searchResults = document.select(".wrapper.clearfix");

        for (Element productElement : searchResults) {
            try {
                ColesProduct colesProduct = ColesProduct.fromProductElement(productElement);
                colesProducts.add(colesProduct);
            } catch (Exception e) {
                logger.error("Unable to extract product from: " + productElement, e);
            }
        }
    }


    public List<ColesProduct> getColesProducts() {
        return colesProducts;
    }

    public void setColesProducts(List<ColesProduct> colesProducts) {
        this.colesProducts = colesProducts;
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("colesProducts", colesProducts)
                .toString();
    }

    public List<Product> toProducts() {

        List<Product> products = new ArrayList<>();

        for (ColesProduct colesProduct : colesProducts) {
            products.add(colesProduct.toGroceriesCoachProduct());
        }

        return products;
    }
}
