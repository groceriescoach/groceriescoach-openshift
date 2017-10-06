package com.groceriescoach.babyvillage.domain;

import com.groceriescoach.core.domain.GroceriesCoachJsoupProduct;
import com.groceriescoach.core.domain.GroceriesCoachSortType;
import com.groceriescoach.core.domain.ProductInformationUnavailableException;
import com.groceriescoach.core.domain.Store;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

import static com.groceriescoach.core.com.groceriescoach.core.utils.CurrencyUtils.extractPriceFrom;
import static com.groceriescoach.core.domain.Store.BabyVillage;

public class BabyVillageProduct extends GroceriesCoachJsoupProduct {


    private static final long serialVersionUID = 3114823354682171187L;

    BabyVillageProduct(Document productElement, GroceriesCoachSortType sortType) throws ProductInformationUnavailableException {
        super(productElement, sortType);
    }

    @Override
    protected Double extractPriceFromProductElement(Element productElement) {
        final Elements priceElements = productElement.select(".price");
        if (priceElements != null && !priceElements.isEmpty()) {
            final Element priceElement = priceElements.get(0);
            TextNode priceNode = null;
            if (priceElement.childNodeSize() == 1) {
                priceNode = (TextNode) priceElement.childNode(0);
            } else if (priceElement.childNodeSize() == 2) {
                priceNode = (TextNode) priceElement.childNode(1);
            }
            if (priceNode != null) {
                return extractPriceFrom(priceNode.text(), null);
            }
        }

        return null;
    }

    @Override
    protected Double extractSavingFromProductElement(Element productElement) {
        return null;
    }

    @Override
    protected Double extractOldPriceFromProductElement(Element productElement) {
        final Elements rrpElements = productElement.select(".rrp");
        if (rrpElements != null && !rrpElements.isEmpty()) {
            return extractPriceFrom(rrpElements.get(0).text(), null);
        } else {
            return null;
        }
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
        final Elements imageProductElements = productElement.select(".image");
        if (imageProductElements != null && !imageProductElements.isEmpty()) {
            final String style = imageProductElements.get(0).attr("style");
            return "https://www.babyvillage.com.au" + style.replace("background-image: url('", "").replace("');", "");
        }
        return null;
    }

    @Override
    protected String extractUrlFromProductElement(Element productElement) {
        Elements productLinkElements = productElement.select("a");
        if (productLinkElements != null && !productLinkElements.isEmpty()) {
            return "https://www.babyvillage.com.au" + productLinkElements.get(0).attr("href");
        } else {
            return "https://www.babyvillage.com.au";
        }
    }

    @Override
    protected String extractNameFromProductElement(Element productElement) {
        return productElement.select(".name").text();
    }


    @Override
    public Store getStore() {
        return BabyVillage;
    }
}

