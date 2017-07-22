package com.groceriescoach.babysavings.domain;

import com.groceriescoach.core.com.groceriescoach.core.utils.StringUtils;
import com.groceriescoach.core.domain.GroceriesCoachJsoupProduct;
import com.groceriescoach.core.domain.GroceriesCoachSortType;
import com.groceriescoach.core.domain.ProductInformationUnavailableException;
import com.groceriescoach.core.domain.Store;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import static com.groceriescoach.core.com.groceriescoach.core.utils.StringUtils.removeCurrencySymbols;
import static com.groceriescoach.core.domain.Store.BabySavings;

public class BabySavingsProduct extends GroceriesCoachJsoupProduct {

    private static final long serialVersionUID = 3437834740992772993L;

    public BabySavingsProduct(Element productElement, GroceriesCoachSortType sortType)
            throws ProductInformationUnavailableException {
        super(productElement, sortType);
    }

    @Override
    protected String extractBrandFromProductElement(Element productElement) {
        return null;
    }

    @Override
    protected String extractDescriptionFromProductElement(Element productElement) {
        return null;
    }

    @Override
    protected String extractImageFromProductElement(Element productElement) {
        return productElement.select(".product-image img").get(0).attr("src");
    }

    @Override
    protected String extractNameFromProductElement(Element productElement) {
        return productElement.select(".product-name").get(0).text();
    }

    @Override
    protected Double extractOldPriceFromProductElement(Element productElement) {
        final Elements oldPriceElements = productElement.select(".old-price .price");
        if (oldPriceElements != null && !oldPriceElements.isEmpty()) {
            String oldPriceText = oldPriceElements.get(0).text();
            if (StringUtils.isNotBlank(oldPriceText)) {
                return Double.parseDouble(removeCurrencySymbols(oldPriceText));
            }
        }
        return null;
    }

    @Override
    protected Double extractPriceFromProductElement(Element productElement) {
        Elements priceElements = productElement.select(".special-price .price");
        if (priceElements == null || priceElements.isEmpty()) {
            priceElements = productElement.select(".regular-price .price");
        }

        String specialPriceText = StringUtils.trimToEmpty(priceElements.get(0).text());
        if (StringUtils.isNotBlank(specialPriceText)) {
            return Double.parseDouble(removeCurrencySymbols(specialPriceText));
        } else {
            return 0D;
        }
    }

    @Override
    protected Double extractSavingFromProductElement(Element productElement) {
        return null;
    }

    @Override
    protected String extractUrlFromProductElement(Element productElement) {
        return productElement.select(".product-image").attr("href");
    }

    @Override
    public Store getStore() {
        return BabySavings;
    }
}
