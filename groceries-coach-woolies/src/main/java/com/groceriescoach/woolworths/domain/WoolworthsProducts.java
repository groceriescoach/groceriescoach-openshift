package com.groceriescoach.woolworths.domain;

import com.groceriescoach.core.domain.Product;

import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.lang3.ArrayUtils.isNotEmpty;

public class WoolworthsProducts {

    private WoolworthsProduct woolworthsProducts[];


    public WoolworthsProduct[] getWoolworthsProducts() {
        return woolworthsProducts;
    }

    public void setWoolworthsProducts(WoolworthsProduct[] woolworthsProducts) {
        this.woolworthsProducts = woolworthsProducts;
    }

/*
    public static List<Product> toProducts(WoolworthsProducts products) {
        if (products != null) {
            return products.toProducts();
        } else {
            return new ArrayList<>();
        }
    }
*/

/*
    private List<Product> toProducts() {
        if (isNotEmpty(woolworthsProducts)) {
            List<Product> products = new ArrayList<>();

            for (WoolworthsProduct woolworthsProduct : woolworthsProducts) {
                products.addAll(woolworthsProduct.toProducts());
            }
            return products;

        }
        return null;
    }
*/
}
