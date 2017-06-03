package com.groceriescoach.babyandtoddlertown.domain;

import com.groceriescoach.core.com.groceriescoach.core.utils.StringUtils;
import com.groceriescoach.core.domain.Product;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import static com.groceriescoach.core.domain.Store.BabyAndToddlerTown;

public class BabyAndToddlerTownProduct extends Product {


    public static BabyAndToddlerTownProduct fromProductElement(Element productElement) {

        BabyAndToddlerTownProduct product = null;
        product = new BabyAndToddlerTownProduct();
        product.setName(extractNameFromProductElement(productElement));
        product.setImageUrl(extractImageFromProductElement(productElement));
        product.setUrl(extractUrlFromProductElement(productElement));
        product.setPrice(extractPriceFromProductElement(productElement));
        product.setWasPrice(extractOldPriceFromProductElement(productElement));
        product.setSaving(product.calculateSavings());
        product.setStore(BabyAndToddlerTown);
        return product;
    }

    private static String extractUrlFromProductElement(Element productElement) {
        return productElement.select(".product-name a").get(0).attr("href");
    }


    private static String extractNameFromProductElement(Element productElement) {
        return productElement.select(".product-name a").get(0).text();
    }

    private static String extractImageFromProductElement(Element productElement) {
        return productElement.select(".product-image img").get(0).attr("src");
    }

    private static Double extractPriceFromProductElement(Element productElement) {
        String price = productElement.select(".special-price .price").get(0).text();
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

