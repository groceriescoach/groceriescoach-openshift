package com.groceriescoach.babybunting.domain;

import com.groceriescoach.core.com.groceriescoach.core.utils.StringUtils;
import com.groceriescoach.core.domain.Product;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import static com.groceriescoach.core.domain.Store.BabyBunting;

public class BabyBuntingProduct extends Product {


    public static BabyBuntingProduct fromProductElement(Element productElement) {
        BabyBuntingProduct product = null;
        product = new BabyBuntingProduct();
        product.setName(extractNameFromProductElement(productElement));
        product.setImageUrl(extractImageFromProductElement(productElement));
        product.setUrl(extractUrlFromProductElement(productElement));
        product.setPrice(extractPriceFromProductElement(productElement));
        product.setWasPrice(extractOldPriceFromProductElement(productElement));
        product.calculateSavings();
        product.calculateUnitPrice();
        product.setStore(BabyBunting);
        return product;
    }

    private static String extractUrlFromProductElement(Element productElement) {
        return productElement.select(".product-name a").get(0).attr("href");
    }

    private static String extractNameFromProductElement(Element productElement) {
        return productElement.select(".product-name a").get(0).text();
    }

    private static String extractImageFromProductElement(Element productElement) {
        return productElement.select(".product-image-contents img").get(0).attr("src");
    }

    private static Double extractPriceFromProductElement(Element productElement) {
        Elements priceElements = productElement.select(".regular-price .price");
        if (priceElements == null || priceElements.isEmpty()) {
            priceElements = productElement.select(".special-price .price");
        }
        String price = priceElements.get(0).text();
        if (StringUtils.isNotBlank(price) && price.startsWith("$")) {
            return Double.parseDouble(StringUtils.removeCurrencySymbols(price));
        }
        return 0D;
    }

    public static Double extractOldPriceFromProductElement(Element productElement) {
        Elements oldPriceElements = productElement.select(".old-price .price");
        if (oldPriceElements != null && !oldPriceElements.isEmpty()) {
            Element oldPriceElement = oldPriceElements.get(0);
            if (oldPriceElement != null) {
                String price = oldPriceElement.text();
                if (StringUtils.isNotBlank(price) && price.startsWith("$")) {
                    return Double.parseDouble(StringUtils.removeCurrencySymbols(price));
                }
                return 0D;
            }
        }
        return null;
    }

}

