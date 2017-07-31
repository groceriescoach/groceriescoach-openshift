package com.groceriescoach.core.domain;

import org.jsoup.nodes.Element;

public abstract class GroceriesCoachJsoupProduct extends GroceriesCoachProduct {


    private static final long serialVersionUID = 4064378611395172246L;

    public GroceriesCoachJsoupProduct(Element productElement, GroceriesCoachSortType sortType) throws ProductInformationUnavailableException {
        preProductElementExtraction(sortType);
        extractFromProductElement(productElement, sortType);
        postProductElementExtraction(sortType);
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


}
