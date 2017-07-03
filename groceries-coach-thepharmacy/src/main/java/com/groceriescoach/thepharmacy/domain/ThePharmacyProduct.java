package com.groceriescoach.thepharmacy.domain;

import com.groceriescoach.core.com.groceriescoach.core.utils.StringUtils;
import com.groceriescoach.core.domain.GroceriesCoachJsoupProduct;
import com.groceriescoach.core.domain.GroceriesCoachSortType;
import com.groceriescoach.core.domain.ProductInformationUnavailableException;
import com.groceriescoach.core.domain.Store;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import static com.groceriescoach.core.domain.Store.ThePharmacy;

public class ThePharmacyProduct extends GroceriesCoachJsoupProduct {

    private static final long serialVersionUID = -2794171389255975633L;

    ThePharmacyProduct(Element productElement, GroceriesCoachSortType sortType) throws ProductInformationUnavailableException {
        super(productElement, sortType);
    }

    @Override
    public Store getStore() {
        return ThePharmacy;
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
        return productElement.select("img").get(0).attr("src");
    }

    @Override
    protected String extractNameFromProductElement(Element productElement) {
        return productElement.select(".product-name").get(0).text();
    }

    @Override
    protected Double extractOldPriceFromProductElement(Element productElement) {
        final Elements oldPriceElements = productElement.select(".old-price .price");
        if (oldPriceElements != null && !oldPriceElements.isEmpty()) {
            final Element oldPriceElement = oldPriceElements.get(0);
            String oldPrice = StringUtils.trimToEmpty(oldPriceElement.text());
            if (StringUtils.isNotBlank(oldPrice) && oldPrice.startsWith("$")) {
                return Double.parseDouble(StringUtils.removeCurrencySymbols(oldPrice));
            }
        }
        return null;
    }

    @Override
    protected Double extractPriceFromProductElement(Element productElement) {
        Element priceElement = null;
        Elements priceElements = productElement.select(".special-price .price");
        if (priceElements == null || priceElements.isEmpty()) {
            priceElements = productElement.select(".regular-price .price");
        }
        priceElement = priceElements.get(0);
        String specialPrice = StringUtils.trimToEmpty(priceElement.text());
        if (StringUtils.isNotBlank(specialPrice) && specialPrice.startsWith("$")) {
            return Double.parseDouble(StringUtils.removeCurrencySymbols(specialPrice));
        }
        return null;
    }

    @Override
    protected Double extractSavingFromProductElement(Element productElement) {
        return null;
    }

    @Override
    protected String extractUrlFromProductElement(Element productElement) {
        return productElement.select(".product-name a").get(0).attr("href");
    }
}
