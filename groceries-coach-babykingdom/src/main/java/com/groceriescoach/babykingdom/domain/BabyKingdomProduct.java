package com.groceriescoach.babykingdom.domain;

import com.groceriescoach.core.com.groceriescoach.core.utils.StringUtils;
import com.groceriescoach.core.domain.Product;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

import static com.groceriescoach.core.domain.Store.BabyKingdom;

public class BabyKingdomProduct extends Product {


    public static BabyKingdomProduct fromProductElement(Element productElement) {
        BabyKingdomProduct babyKingdomProduct = null;
        babyKingdomProduct = new BabyKingdomProduct();
        babyKingdomProduct.setName(extractNameFromProductElement(productElement));
        babyKingdomProduct.setImageUrl(extractImageFromProductElement(productElement));
        babyKingdomProduct.setUrl(extractUrlFromProductElement(productElement));
        babyKingdomProduct.setPrice(extractPriceFromProductElement(productElement));
        babyKingdomProduct.setWasPrice(extractOldPriceFromProductElement(productElement));
        babyKingdomProduct.calculateSavings();
        babyKingdomProduct.calculateUnitPrice();
        babyKingdomProduct.setStore(BabyKingdom);
        return babyKingdomProduct;
    }

    private static String extractUrlFromProductElement(Element productElement) {
        return "https://www.babykingdom.com.au/" + productElement.select(".moredetails a").get(0).attr("href");
    }

    private static String extractNameFromProductElement(Element productElement) {
        return productElement.select(".prodname").get(0).text();
    }

    private static String extractImageFromProductElement(Element productElement) {
        return "https://www.babykingdom.com.au/" + productElement.select("img.corner").get(0).attr("src");
    }

    private static Double extractPriceFromProductElement(Element productElement) {
        final Elements prodPriceElements = productElement.select(".prodprice td");
        if (prodPriceElements != null && !prodPriceElements.isEmpty()) {
            String price = prodPriceElements.get(0).text();
            if (StringUtils.isNotBlank(price)) {
                price = price.replaceAll("AU", "");
                return Double.parseDouble(StringUtils.removeCurrencySymbols(price));
            }
        } else {
            final Elements specialPriceElements = productElement.select(".prodprice_special td");
            if (specialPriceElements != null && !specialPriceElements.isEmpty()) {
                final Element specialPriceElement = specialPriceElements.get(0);
                for (Node childNode : specialPriceElement.childNodes()) {
                    if (childNode instanceof TextNode) {
                        String specialPrice = StringUtils.trimToEmpty(((TextNode) childNode).text());
                        if (specialPrice.startsWith("AU")) {
                            specialPrice = specialPrice.replaceAll("AU", "");
                            return Double.parseDouble(StringUtils.removeCurrencySymbols(specialPrice));
                        }
                    }
                }
            }
        }
        return 0D;
    }

    public static Double extractOldPriceFromProductElement(Element productElement) {
        Elements oldPriceElements = productElement.select(".prodrrpprice");
        if (oldPriceElements != null && !oldPriceElements.isEmpty()) {
            Element oldPriceElement = oldPriceElements.get(0);
            if (oldPriceElement != null) {
                String price = StringUtils.trimToEmpty(oldPriceElement.text());
                if (StringUtils.isNotBlank(price) && price.startsWith("RRP")) {
                    price = StringUtils.trimToEmpty(price.replaceAll("RRP", ""));
                }
                if (StringUtils.isNotBlank(price) && price.startsWith("AU")) {
                    price = StringUtils.trimToEmpty(price.replaceAll("AU", ""));
                }
                if (StringUtils.isNotBlank(price)) {
                    return Double.parseDouble(StringUtils.removeCurrencySymbols(price));
                }
                return 0D;
            }
        }
        return null;
    }

}

