package com.groceriescoach.chemistwarehouse.domain;

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

public class ChemistWarehouseSearchResult implements Serializable {

    private static final long serialVersionUID = -7704062795180569959L;

    private List<ChemistWarehouseProduct> chemistWarehouseProducts;

    private static final Logger logger = LoggerFactory.getLogger(ChemistWarehouseSearchResult.class);

    public ChemistWarehouseSearchResult(Document document) {

        chemistWarehouseProducts = new ArrayList<>();

        Elements searchResults = document.select(".product-container");

        for (Element productElement : searchResults) {
            try {
                ChemistWarehouseProduct chemistWarehouseProduct = ChemistWarehouseProduct.fromProductElement(productElement);
                chemistWarehouseProducts.add(chemistWarehouseProduct);
            } catch (Exception e) {
                logger.error("Unable to extract product from: " + productElement, e);
            }
        }
    }


    public List<ChemistWarehouseProduct> getChemistWarehouseProducts() {
        return chemistWarehouseProducts;
    }

    public void setChemistWarehouseProducts(List<ChemistWarehouseProduct> chemistWarehouseProducts) {
        this.chemistWarehouseProducts = chemistWarehouseProducts;
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("chemistWarehouseProducts", chemistWarehouseProducts)
                .toString();
    }

    public List<Product> toProducts() {

        List<Product> products = new ArrayList<>();

        for (ChemistWarehouseProduct chemistWarehouseProduct : chemistWarehouseProducts) {
            products.add(chemistWarehouseProduct.toGroceriesCoachProduct());
        }

        return products;
    }
}
