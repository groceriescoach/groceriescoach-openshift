package com.groceriescoach.amcal.domain;

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

public class AmcalSearchResult implements Serializable {

    private static final long serialVersionUID = -7704062795180569959L;

    private List<AmcalProduct> amcalProducts;

    private static final Logger logger = LoggerFactory.getLogger(AmcalSearchResult.class);

    public AmcalSearchResult(Document document) {

        amcalProducts = new ArrayList<>();

        Elements searchResults = document.select(".product");

        for (Element productElement : searchResults) {
            try {
                AmcalProduct amcalProduct = AmcalProduct.fromProductElement(productElement);
                if (amcalProduct != null) {
                    amcalProducts.add(amcalProduct);
                }
            } catch (Exception e) {
                logger.error("Unable to extract product from: " + productElement, e);
            }
        }
    }


    public List<AmcalProduct> getAmcalProducts() {
        return amcalProducts;
    }

    public void setAmcalProducts(List<AmcalProduct> amcalProducts) {
        this.amcalProducts = amcalProducts;
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("amcalProducts", amcalProducts)
                .toString();
    }

    public List<Product> toProducts() {

        List<Product> products = new ArrayList<>();

        for (AmcalProduct amcalProduct : amcalProducts) {
            products.add(amcalProduct.toGroceriesCoachProduct());
        }

        return products;
    }
}
