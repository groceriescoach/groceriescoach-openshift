package com.groceriescoach.babykingdom.domain;

import com.groceriescoach.core.com.groceriescoach.core.utils.CurrencyUtils;
import com.groceriescoach.core.com.groceriescoach.core.utils.StringUtils;
import com.groceriescoach.core.domain.GroceriesCoachJsoupProduct;
import com.groceriescoach.core.domain.GroceriesCoachSortType;
import com.groceriescoach.core.domain.ProductInformationUnavailableException;
import com.groceriescoach.core.domain.Store;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

import static com.groceriescoach.core.domain.Store.BabyKingdom;

public class BabyKingdomProduct extends GroceriesCoachJsoupProduct {


    BabyKingdomProduct(Element productElement, GroceriesCoachSortType sortType) throws ProductInformationUnavailableException {
        super(productElement, sortType);
    }

    @Override
    protected String extractUrlFromProductElement(Element productElement) {
        return "https://www.babykingdom.com.au/" + productElement.select(".moredetails a").get(0).attr("href");
    }

    @Override
    protected String extractNameFromProductElement(Element productElement) {
        return productElement.select(".prodname").get(0).text();
    }

    @Override
    protected String extractImageFromProductElement(Element productElement) {
        return "https://www.babykingdom.com.au/" + productElement.select("img.corner").get(0).attr("src");
    }

    @Override
    protected Double extractPriceFromProductElement(Element productElement) {
        final Elements prodPriceElements = productElement.select(".prodprice td");
        if (prodPriceElements != null && !prodPriceElements.isEmpty()) {
            String price = prodPriceElements.get(0).text();
            if (StringUtils.isNotBlank(price)) {
                price = price.replaceAll("AU", "");
                return CurrencyUtils.extractPriceFrom(price, 0D);
            }
        } else {
            final Elements specialPriceElements = productElement.select(".prodprice_special td");
            if (specialPriceElements != null && !specialPriceElements.isEmpty()) {
                final Element specialPriceElement = specialPriceElements.get(0);
                for (Node childNode : specialPriceElement.childNodes()) {
                    if (childNode instanceof TextNode) {
                        String specialPrice = StringUtils.trimToEmpty(((TextNode) childNode).text());
                        if (specialPrice.startsWith("AU")) {
                            specialPrice = specialPrice.replaceAll("AU", "");
                            return CurrencyUtils.extractPriceFrom(specialPrice, 0D);
                        }
                    }
                }
            }
        }
        return 0D;
    }

    @Override
    protected Double extractSavingFromProductElement(Element productElement) {
        return null;
    }

    @Override
    protected Double extractOldPriceFromProductElement(Element productElement) {
        Elements oldPriceElements = productElement.select(".prodrrpprice");
        if (oldPriceElements != null && !oldPriceElements.isEmpty()) {
            Element oldPriceElement = oldPriceElements.get(0);
            if (oldPriceElement != null) {
                String price = StringUtils.trimToEmpty(oldPriceElement.text());
                if (StringUtils.isNotBlank(price) && price.startsWith("RRP")) {
                    price = StringUtils.trimToEmpty(price.replaceAll("RRP", ""));
                }
                if (StringUtils.isNotBlank(price) && price.startsWith("AU")) {
                    price = StringUtils.trimToEmpty(price.replaceAll("AU", ""));
                }
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
        return BabyKingdom;
    }
}

