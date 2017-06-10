package com.groceriescoach.woolworths.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.groceriescoach.core.com.groceriescoach.core.utils.CollectionUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class Product implements Serializable {

    private static final long serialVersionUID = -3258096516203229499L;


    @JsonProperty("Products")
    private ProductDetails products[];
    @JsonProperty("Name")
    private String name;


    public ProductDetails[] getProducts() {
        return products;
    }

    public void setProducts(ProductDetails[] products) {
        this.products = products;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("products", products)
                .append("name", name)
                .toString();
    }

/*
    public static List<Product> toProducts(WoolworthsProduct[] woolworthsProducts) {
        List<Product> products = new ArrayList<>();

        for (WoolworthsProduct woolworthsProduct : woolworthsProducts) {
            products.addAll(woolworthsProduct.toProducts());
        }
        return products;
    }
*/

    public static List<com.groceriescoach.core.domain.Product> toProducts(List<Product> woolworthsProducts, String keywords) {
        List<com.groceriescoach.core.domain.Product> products = new ArrayList<>();

        if (!CollectionUtils.isEmpty(woolworthsProducts)) {
            for (Product woolworthsProduct : woolworthsProducts) {
                products.addAll(woolworthsProduct.toProducts(keywords));
            }
        }
        return products;
    }


    public List<com.groceriescoach.core.domain.Product> toProducts(String keywords) {
        return ProductDetails.toProducts(products, keywords);

    }
}


