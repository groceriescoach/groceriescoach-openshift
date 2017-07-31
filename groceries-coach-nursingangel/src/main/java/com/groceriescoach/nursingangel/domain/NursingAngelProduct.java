package com.groceriescoach.nursingangel.domain;

import com.groceriescoach.core.com.groceriescoach.core.utils.StringUtils;
import com.groceriescoach.core.domain.GroceriesCoachJsoupProduct;
import com.groceriescoach.core.domain.GroceriesCoachSortType;
import com.groceriescoach.core.domain.ProductInformationUnavailableException;
import com.groceriescoach.core.domain.Store;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import static com.groceriescoach.core.domain.Store.NursingAngel;

public class NursingAngelProduct extends GroceriesCoachJsoupProduct {

    private static final long serialVersionUID = -5620627161169645941L;

    NursingAngelProduct(Element productElement, GroceriesCoachSortType sortType) throws ProductInformationUnavailableException {
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
        return productElement.select(".product-title-thumbnail").get(0).attr("href");
    }

    protected String extractNameFromProductElement(Element productElement) {
        return productElement.select(".product-title-thumbnail").get(0).text();
    }

    protected String extractImageFromProductElement(Element productElement) {
        return "http://www.nursingangel.com.au" + productElement.select(".product-image").get(0).attr("src");
    }

    protected Double extractPriceFromProductElement(Element productElement) {
        Elements priceElements = productElement.select(".price span");
        if (priceElements != null && !priceElements.isEmpty()) {
            for (Element priceElement : priceElements) {
                if (priceElement.hasAttr("itemprop") && priceElement.attr("itemprop").equalsIgnoreCase("price")) {
                    return Double.parseDouble(StringUtils.removeCurrencySymbols(priceElement.text()));
                }
            }
        }
        return 0D;
    }

    @Override
    protected Double extractSavingFromProductElement(Element productElement) {
        return null;
    }

    protected Double extractOldPriceFromProductElement(Element productElement) {
        Elements oldPriceElements = productElement.select(".rrp-wrap");
        if (oldPriceElements != null && !oldPriceElements.isEmpty()) {
            Element oldPriceElement = oldPriceElements.get(0);
            if (oldPriceElement != null) {
                String price = StringUtils.trimToEmpty(oldPriceElement.text());
                if (StringUtils.isNotBlank(price) && price.startsWith("RRP")) {
                    price = StringUtils.trimToEmpty(price.replaceAll("RRP", ""));
                }
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
        return NursingAngel;
    }
}

