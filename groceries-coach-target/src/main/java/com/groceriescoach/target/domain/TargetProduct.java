package com.groceriescoach.target.domain;

import com.groceriescoach.core.com.groceriescoach.core.utils.StringUtils;
import com.groceriescoach.core.domain.GroceriesCoachJsoupProduct;
import com.groceriescoach.core.domain.GroceriesCoachSortType;
import com.groceriescoach.core.domain.ProductInformationUnavailableException;
import com.groceriescoach.core.domain.Store;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import static com.groceriescoach.core.domain.Store.Target;

public class TargetProduct extends GroceriesCoachJsoupProduct {

    TargetProduct(Element productElement, GroceriesCoachSortType sortType) throws ProductInformationUnavailableException {
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

    protected String extractUrlFromProductElement(Element productElement) {
        return "https://www.target.com.au" + productElement.select(".name-heading a").get(0).attr("href");
    }

    protected String extractNameFromProductElement(Element productElement) {
        return productElement.select(".name-heading a").get(0).text();
    }

    protected String extractImageFromProductElement(Element productElement) {
        return "https://www.target.com.au" + productElement.select(".thumb-img").get(0).attr("src");
    }

    protected Double extractPriceFromProductElement(Element productElement) {
        Elements priceElements = productElement.select(".price-info .price-regular");
        if (priceElements == null || priceElements.isEmpty()) {
            priceElements = productElement.select(".price-info .price-reduced");
        }
        String price = priceElements.get(0).text();

        if (StringUtils.isNotBlank(price) && price.startsWith("$")) {
            return Double.parseDouble(StringUtils.removeCurrencySymbols(price));
        }
        return 0D;
    }

    @Override
    protected Double extractSavingFromProductElement(Element productElement) {
        return null;
    }

    protected Double extractOldPriceFromProductElement(Element productElement) {
        Elements oldPriceElements = productElement.select(".price-info .was-price");
        if (oldPriceElements != null && !oldPriceElements.isEmpty()) {
            Element oldPriceElement = oldPriceElements.get(0);
            if (oldPriceElement != null) {
                String price = oldPriceElement.text();
                price = price.replaceAll("Was ", "");
                if (StringUtils.isNotBlank(price) && price.startsWith("$")) {
                    return Double.parseDouble(StringUtils.removeCurrencySymbols(price));
                }
                return 0D;
            }
        }
        return null;
    }

    @Override
    public Store getStore() {
        return Target;
    }
}

