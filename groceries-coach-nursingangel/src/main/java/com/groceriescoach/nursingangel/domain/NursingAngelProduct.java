package com.groceriescoach.nursingangel.domain;

import com.groceriescoach.core.com.groceriescoach.core.utils.StringUtils;
import com.groceriescoach.core.domain.GroceriesCoachSortType;
import com.groceriescoach.core.domain.Product;
import com.groceriescoach.core.domain.ProductInformationUnavailableException;
import com.groceriescoach.core.domain.Store;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import static com.groceriescoach.core.domain.Store.NursingAngel;

public class NursingAngelProduct extends Product {

    NursingAngelProduct(Element productElement, GroceriesCoachSortType sortType) throws ProductInformationUnavailableException {
        super(productElement, sortType);
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

    private static Double extractOldPriceFromProductElement(Element productElement) {
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

    @Override
    protected void extractFromProductElement(Element productElement, GroceriesCoachSortType sortType) {
        setName(extractNameFromProductElement(productElement));
        setImageUrl(extractImageFromProductElement(productElement));
        setUrl(extractUrlFromProductElement(productElement));
        setPrice(extractPriceFromProductElement(productElement));
        setWasPrice(extractOldPriceFromProductElement(productElement));
    }

    @Override
    public Store getStore() {
        return NursingAngel;
    }
}

