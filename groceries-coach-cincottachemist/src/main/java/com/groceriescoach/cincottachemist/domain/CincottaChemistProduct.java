package com.groceriescoach.cincottachemist.domain;

import com.groceriescoach.core.com.groceriescoach.core.utils.CurrencyUtils;
import com.groceriescoach.core.com.groceriescoach.core.utils.StringUtils;
import com.groceriescoach.core.domain.GroceriesCoachJsoupProduct;
import com.groceriescoach.core.domain.GroceriesCoachSortType;
import com.groceriescoach.core.domain.ProductInformationUnavailableException;
import com.groceriescoach.core.domain.Store;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import static com.groceriescoach.core.domain.Store.CincottaChemist;

public class CincottaChemistProduct extends GroceriesCoachJsoupProduct {

    private static final long serialVersionUID = 3937397817855174721L;

    public CincottaChemistProduct(Element productElement, GroceriesCoachSortType sortType)
            throws ProductInformationUnavailableException {
        super(productElement, sortType);
    }

    @Override
    public Store getStore() {
        return CincottaChemist;
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
        return productElement.select(".product-name a").get(0).attr("title");
    }

    @Override
    protected Double extractOldPriceFromProductElement(Element productElement) {
        final Elements oldPriceElements = productElement.select(".old-price .price");
        if (oldPriceElements != null && !oldPriceElements.isEmpty()) {
            final Element oldPriceElement = oldPriceElements.get(0);
            String oldPrice = StringUtils.trimToEmpty(oldPriceElement.text());
            return CurrencyUtils.extractPriceFrom(oldPrice, null);
        }
        return null;
    }

    @Override
    protected Double extractPriceFromProductElement(Element productElement) {
        Elements priceElements = productElement.select(".special-price .price");
        if (priceElements == null || priceElements.isEmpty()) {
            priceElements = productElement.select(".regular-price .price");
        }

        if (priceElements != null && !priceElements.isEmpty()) {
            final Element priceElement = priceElements.get(0);
            final String price = StringUtils.trimToEmpty(priceElement.text());

            if (StringUtils.isNotBlank(price) && price.startsWith("$")) {
                return CurrencyUtils.extractPriceFrom(price, null);
            }
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
