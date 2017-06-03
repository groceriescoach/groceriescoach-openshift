package com.groceriescoach.pharmacy4less.domain;

import com.groceriescoach.core.com.groceriescoach.core.utils.StringUtils;
import com.groceriescoach.core.domain.Product;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import static com.groceriescoach.core.domain.Store.Pharmacy4Less;

public class Pharmacy4LessProduct extends Product {


    public static Pharmacy4LessProduct fromProductElement(Element productElement) {

        Pharmacy4LessProduct product = null;
        product = new Pharmacy4LessProduct();
        product.setName(extractNameFromProductElement(productElement));
        product.setImageUrl(extractImageFromProductElement(productElement));
        product.setUrl(extractUrlFromProductElement(productElement));
        product.setPrice(extractPriceFromProductElement(productElement));
        product.setWasPrice(extractOldPriceFromProductElement(productElement));
        product.setSaving(product.calculateSavings());
        product.setStore(Pharmacy4Less);
        return product;
    }

    private static String extractUrlFromProductElement(Element productElement) {
        return productElement.select(".sli_content").get(0).attr("data-direct-url");
    }


    private static String extractNameFromProductElement(Element productElement) {
        return productElement.select(".product-name").get(0).text();
    }

    private static String extractImageFromProductElement(Element productElement) {
        return productElement.select("img").get(0).attr("src");
    }

    private static Double extractPriceFromProductElement(Element productElement) {
        String price = productElement.select(".sli_price").get(0).text();
        if (StringUtils.isNotBlank(price)) {
            return Double.parseDouble(StringUtils.removeCurrencySymbols(price));
        }
        return 0D;
    }

    public static Double extractOldPriceFromProductElement(Element productElement) {
        Elements oldPriceElements = productElement.select(".sli_orig_price");
        if (oldPriceElements != null && !oldPriceElements.isEmpty()) {
            Element oldPriceElement = oldPriceElements.get(0);
            if (oldPriceElement != null) {
                String price = oldPriceElement.text();
                if (StringUtils.isNotBlank(price)) {
                    return Double.parseDouble(StringUtils.removeCurrencySymbols(price));
                }
                return 0D;
            }
        }
        return null;
    }

}

