package com.groceriescoach.priceline.domain;


import com.groceriescoach.core.com.groceriescoach.core.utils.StringUtils;
import com.groceriescoach.core.domain.GroceriesCoachJsoupProduct;
import com.groceriescoach.core.domain.GroceriesCoachSortType;
import com.groceriescoach.core.domain.ProductInformationUnavailableException;
import com.groceriescoach.core.domain.Store;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import static com.groceriescoach.core.domain.Store.Priceline;

public class PricelineProduct extends GroceriesCoachJsoupProduct {

    PricelineProduct(Element productElement, GroceriesCoachSortType sortType) throws ProductInformationUnavailableException {
        super(productElement, sortType);
    }

    @Override
    protected String extractUrlFromProductElement(Element productElement) {
        return productElement.select(".product-image-container a").get(0).attr("href");
    }

    @Override
    public Store getStore() {
        return Priceline;
    }

    @Override
    protected String extractBrandFromProductElement(Element productElement) {
        return productElement.select(".product-brand").get(0).text();
    }

    @Override
    protected String extractDescriptionFromProductElement(Element productElement) {
        return null;
    }

    @Override
    protected String extractNameFromProductElement(Element productElement) {
        return productElement.select(".product-link").get(0).text();
    }

    @Override
    protected Double extractOldPriceFromProductElement(Element productElement) {
        Elements oldPriceElements = productElement.select(".old-price .price");
        if (oldPriceElements.isEmpty()) {
            return null;
        } else {
            return Double.parseDouble(StringUtils.removeCurrencySymbols(oldPriceElements.text()));
        }
    }

    @Override
    protected String extractImageFromProductElement(Element productElement) {
        return productElement.select("img").get(0).attr("src");
    }

    @Override
    protected Double extractPriceFromProductElement(Element productElement) {
        Elements regularPriceElements = productElement.select(".regular-price .price");
        if (regularPriceElements.isEmpty()) {
            return Double.parseDouble(StringUtils.removeCurrencySymbols(productElement.select(".special-price .price").text()));
        } else {
            return Double.parseDouble(StringUtils.removeCurrencySymbols(regularPriceElements.text()));
        }
    }

    @Override
    protected Double extractSavingFromProductElement(Element productElement) {
        return null;
    }

}
