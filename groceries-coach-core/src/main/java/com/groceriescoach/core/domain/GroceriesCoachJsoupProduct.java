package com.groceriescoach.core.domain;

import org.jsoup.nodes.Element;

public abstract class GroceriesCoachJsoupProduct extends GroceriesCoachProduct {


    public GroceriesCoachJsoupProduct(Element productElement, GroceriesCoachSortType sortType) throws ProductInformationUnavailableException {
        preProductElementExtraction(productElement, sortType);
        extractFromProductElement(productElement, sortType);
        postProductElementExtraction(productElement, sortType);
    }


    protected void preProductElementExtraction(Element productElement, GroceriesCoachSortType sortType) {

    }

    protected void extractFromProductElement(Element productElement, GroceriesCoachSortType sortType) throws ProductInformationUnavailableException {
        setBrand(extractBrandFromProductElement(productElement));
        setDescription(extractDescriptionFromProductElement(productElement));
        setName(extractNameFromProductElement(productElement));
        setImageUrl(extractImageFromProductElement(productElement));
        setPrice(extractPriceFromProductElement(productElement));
        setWasPrice(extractOldPriceFromProductElement(productElement));
        setUrl(extractUrlFromProductElement(productElement));
        setSaving(extractSavingFromProductElement(productElement));
    }


    protected abstract String extractBrandFromProductElement(Element productElement);

    protected abstract String extractDescriptionFromProductElement(Element productElement);

    protected abstract String extractImageFromProductElement(Element productElement);

    protected abstract String extractNameFromProductElement(Element productElement);

    protected abstract Double extractOldPriceFromProductElement(Element productElement);

    protected abstract Double extractPriceFromProductElement(Element productElement);

    protected abstract Double extractSavingFromProductElement(Element productElement);

    protected abstract String extractUrlFromProductElement(Element productElement);

    protected void postProductElementExtraction(Element productElement, GroceriesCoachSortType sortType) {

        calculatePackageSize();
        if (sortType.isUnitPriceRequired()) {
            calculateUnitPrice();
        }
        calculateSavings();
        calculateOldPrice();
    }

}
