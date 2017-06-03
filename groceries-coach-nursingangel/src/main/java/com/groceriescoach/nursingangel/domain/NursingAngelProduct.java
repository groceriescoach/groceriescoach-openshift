package com.groceriescoach.nursingangel.domain;

import com.groceriescoach.core.com.groceriescoach.core.utils.StringUtils;
import com.groceriescoach.core.domain.Product;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import static com.groceriescoach.core.domain.Store.NursingAngel;

public class NursingAngelProduct extends Product {

    public static NursingAngelProduct fromProductElement(Element productElement) {
        NursingAngelProduct product = null;
        product = new NursingAngelProduct();
        product.setName(extractNameFromProductElement(productElement));
        product.setImageUrl(extractImageFromProductElement(productElement));
        product.setUrl(extractUrlFromProductElement(productElement));
        product.setPrice(extractPriceFromProductElement(productElement));
        product.setWasPrice(extractOldPriceFromProductElement(productElement));
        product.setSaving(product.calculateSavings());
        product.setStore(NursingAngel);
        return product;
    }

    private static Double extractSavingsFromProductElement(Element productElement) {
        Elements saveElements = productElement.select(".save");
        if (saveElements != null && !saveElements.isEmpty()) {
            Element saveElement = saveElements.get(0);
            String savingText = saveElement.text();
            if (StringUtils.isNotBlank(savingText)) {
                savingText = savingText.replaceAll("Save ", "");
                savingText = StringUtils.removeCurrencySymbols(savingText);
                return Double.parseDouble(savingText);
            }
        }
        return 0D;
    }

    private static String extractUrlFromProductElement(Element productElement) {
        return productElement.select(".product-title-thumbnail").get(0).attr("href");
    }

    private static String extractNameFromProductElement(Element productElement) {
        return productElement.select(".product-title-thumbnail").get(0).text();
    }

    private static String extractImageFromProductElement(Element productElement) {
        return "http://www.nursingangel.com.au" + productElement.select(".product-image").get(0).attr("src");
    }

    private static Double extractPriceFromProductElement(Element productElement) {
        Elements priceElements = productElement.select(".price span");
        if (priceElements != null && !priceElements.isEmpty()) {
            for (Element priceElement : priceElements) {
                if (priceElement.hasAttr("itemprop") && priceElement.attr("itemprop").equalsIgnoreCase("price")) {
                    return Double.parseDouble(StringUtils.removeCurrencySymbols(priceElement.text()));
                }
            }
        }
        return 0D;
    }

    public static Double extractOldPriceFromProductElement(Element productElement) {
        Elements oldPriceElements = productElement.select(".rrp-wrap");
        if (oldPriceElements != null && !oldPriceElements.isEmpty()) {
            Element oldPriceElement = oldPriceElements.get(0);
            if (oldPriceElement != null) {
                String price = StringUtils.trimToEmpty(oldPriceElement.text());
                if (StringUtils.isNotBlank(price) && price.startsWith("RRP")) {
                    price = StringUtils.trimToEmpty(price.replaceAll("RRP", ""));
                }
                if (StringUtils.isNotBlank(price) && price.startsWith("$")) {
                    return Double.parseDouble(StringUtils.removeCurrencySymbols(price));
                }
                return 0D;
            }
        }
        return null;
    }

}

