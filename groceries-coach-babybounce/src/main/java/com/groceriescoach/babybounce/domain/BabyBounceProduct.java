package com.groceriescoach.babybounce.domain;

import com.groceriescoach.core.com.groceriescoach.core.utils.StringUtils;
import com.groceriescoach.core.domain.GroceriesCoachSortType;
import com.groceriescoach.core.domain.Product;
import com.groceriescoach.core.domain.ProductInformationUnavailableException;
import com.groceriescoach.core.domain.Store;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.Serializable;

import static com.groceriescoach.core.domain.Store.BabyBounce;

public class BabyBounceProduct extends Product implements Serializable {


    BabyBounceProduct(Element productElement, GroceriesCoachSortType sortType) throws ProductInformationUnavailableException {
        super(productElement, sortType);
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
        return BabyBounce;
    }


    private static String extractUrlFromProductElement(Element productElement) {
        return productElement.select(".product-name").get(0).attr("href");
    }

    private static String extractNameFromProductElement(Element productElement) {
        return productElement.select(".product-name").get(0).text();
    }

    private static String extractImageFromProductElement(Element productElement) {
        return productElement.select(".primary_img img").get(0).attr("src");
    }

    private static Double extractPriceFromProductElement(Element productElement) {
        String price = "";
        Elements specialPriceElements = productElement.select(".special-price .price");
        if (specialPriceElements == null || specialPriceElements.isEmpty()) {
            specialPriceElements = productElement.select(".regular-price .price");
        }
        if (specialPriceElements == null || specialPriceElements.isEmpty()) {
            specialPriceElements = productElement.select(".item-price .price");
        }

        price = specialPriceElements.get(0).text();

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

