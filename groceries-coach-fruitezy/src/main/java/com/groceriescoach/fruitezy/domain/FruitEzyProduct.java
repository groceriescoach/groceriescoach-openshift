package com.groceriescoach.fruitezy.domain;

import com.groceriescoach.core.com.groceriescoach.core.utils.CurrencyUtils;
import com.groceriescoach.core.domain.GroceriesCoachJsoupProduct;
import com.groceriescoach.core.domain.GroceriesCoachSortType;
import com.groceriescoach.core.domain.ProductInformationUnavailableException;
import com.groceriescoach.core.domain.Store;
import org.jsoup.nodes.Element;

import static com.groceriescoach.core.domain.Store.FruitEzy;

public class FruitEzyProduct extends GroceriesCoachJsoupProduct {

    public FruitEzyProduct(Element productElement, GroceriesCoachSortType sortType) throws ProductInformationUnavailableException {
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
        return productElement.select("img").attr("src");
    }

    @Override
    protected String extractNameFromProductElement(Element productElement) {
        return productElement.select("h3").text();
    }

    @Override
    protected Double extractOldPriceFromProductElement(Element productElement) {
        return null;
    }

    @Override
    protected Double extractPriceFromProductElement(Element productElement) {
        return CurrencyUtils.extractPriceFrom(productElement.select(".amount").text(), 0D);
    }

    @Override
    protected Double extractSavingFromProductElement(Element productElement) {
        return null;
    }

    @Override
    protected String extractUrlFromProductElement(Element productElement) {
        return productElement.select("a").attr("href");
    }

    @Override
    public Store getStore() {
        return FruitEzy;
    }
}
