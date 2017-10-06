package com.groceriescoach.babyandtoddlertown.domain;

import com.groceriescoach.core.com.groceriescoach.core.utils.CurrencyUtils;
import com.groceriescoach.core.domain.GroceriesCoachJsoupProduct;
import com.groceriescoach.core.domain.GroceriesCoachSortType;
import com.groceriescoach.core.domain.ProductInformationUnavailableException;
import com.groceriescoach.core.domain.Store;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import static com.groceriescoach.core.domain.Store.BabyAndToddlerTown;

public class BabyAndToddlerTownProduct extends GroceriesCoachJsoupProduct {


    BabyAndToddlerTownProduct(Element productElement, GroceriesCoachSortType sortType) throws ProductInformationUnavailableException {
        super(productElement, sortType);
    }

    @Override
    protected String extractUrlFromProductElement(Element productElement) {
        return productElement.select(".product-name a").get(0).attr("href");
    }


    @Override
    protected String extractNameFromProductElement(Element productElement) {
        return productElement.select(".product-name a").get(0).text();
    }

    @Override
    protected String extractImageFromProductElement(Element productElement) {
        return productElement.select(".product-image img").get(0).attr("src");
    }

    @Override
    protected Double extractPriceFromProductElement(Element productElement) {
        final Elements specialPriceElements = productElement.select(".special-price .price");
        String price = "";
        if (specialPriceElements != null && !specialPriceElements.isEmpty()) {
            price = specialPriceElements.get(0).text();
        } else {
            final Elements priceElements = productElement.select(".regular-price .price");
            price = priceElements.get(0).text();
        }
        return CurrencyUtils.extractPriceFrom(price, null);
    }

    @Override
    protected Double extractSavingFromProductElement(Element productElement) {
        return null;
    }

    @Override
    protected Double extractOldPriceFromProductElement(Element productElement) {
        Elements oldPriceElements = productElement.select(".old-price .price");
        if (oldPriceElements != null && !oldPriceElements.isEmpty()) {
            Element oldPriceElement = oldPriceElements.get(0);
            if (oldPriceElement != null) {
                String price = oldPriceElement.text();
                return CurrencyUtils.extractPriceFrom(price, null);
            }
        }
        return null;
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
    public Store getStore() {
        return BabyAndToddlerTown;
    }
}

