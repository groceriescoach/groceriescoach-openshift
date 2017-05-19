package com.groceriescoach.babybunting.domain;

import com.groceriescoach.core.com.groceriescoach.core.utils.StringUtils;
import com.groceriescoach.core.domain.Product;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.groceriescoach.core.domain.Store.Amcal;

public class BabyBuntingProduct extends Product {


    public static BabyBuntingProduct fromProductElement(Element productElement) {

        BabyBuntingProduct babyBuntingProduct = null;
        babyBuntingProduct = new BabyBuntingProduct();
        babyBuntingProduct.setName(extractNameFromProductElement(productElement));
        babyBuntingProduct.setImageUrl(extractImageFromProductElement(productElement));
        babyBuntingProduct.setUrl(extractUrlFromProductElement(productElement));
        babyBuntingProduct.setPrice(extractPriceFromProductElement(productElement));
        babyBuntingProduct.setWasPrice(extractOldPriceFromProductElement(productElement));
        babyBuntingProduct.setSaving(babyBuntingProduct.calculateSavings());
        return babyBuntingProduct;
    }

    private static String extractUrlFromProductElement(Element productElement) {
        return productElement.select(".product_name a").get(0).attr("href");
    }


    private static String extractNameFromProductElement(Element productElement) {
        return productElement.select(".product_name a").get(0).text();
    }

    private static String extractImageFromProductElement(Element productElement) {
        return "https://www.amcal.com.au" + productElement.select("img").get(0).attr("src");
    }

    private static Double extractPriceFromProductElement(Element productElement) {
        String price = productElement.select(".price").get(0).text();
        if (StringUtils.isNotBlank(price) && price.startsWith("$")) {
            return Double.parseDouble(StringUtils.removeCurrencySymbols(price.substring(1)));
        }
        return 0D;
    }

    public static Double extractOldPriceFromProductElement(Element productElement) {
        Elements oldPriceElements = productElement.select(".old_price");
        if (oldPriceElements != null && !oldPriceElements.isEmpty()) {
            Element oldPriceElement = oldPriceElements.get(0);
            if (oldPriceElement != null) {
                String price = oldPriceElement.text();
                if (StringUtils.isNotBlank(price) && price.startsWith("$")) {
                    return Double.parseDouble(StringUtils.removeCurrencySymbols(price.substring(1)));
                }
                return 0D;
            }
        }
        return null;
    }

}
