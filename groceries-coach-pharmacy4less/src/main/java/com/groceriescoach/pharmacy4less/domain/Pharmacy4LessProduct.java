package com.groceriescoach.pharmacy4less.domain;

import com.groceriescoach.core.com.groceriescoach.core.utils.StringUtils;
import com.groceriescoach.core.domain.GroceriesCoachJsoupProduct;
import com.groceriescoach.core.domain.GroceriesCoachSortType;
import com.groceriescoach.core.domain.ProductInformationUnavailableException;
import com.groceriescoach.core.domain.Store;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import static com.groceriescoach.core.domain.Store.Pharmacy4Less;

public class Pharmacy4LessProduct extends GroceriesCoachJsoupProduct {

    Pharmacy4LessProduct(Element productElement, GroceriesCoachSortType sortType) throws ProductInformationUnavailableException {
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
    public Store getStore() {
        return Pharmacy4Less;
    }

    protected String extractUrlFromProductElement(Element productElement) {
        return productElement.select(".sli_content").get(0).attr("data-direct-url");
    }

    protected String extractNameFromProductElement(Element productElement) {
        return productElement.select(".product-name").get(0).text();
    }

    protected String extractImageFromProductElement(Element productElement) {
        return productElement.select("img").get(0).attr("src");
    }

    protected Double extractPriceFromProductElement(Element productElement) {
        String price = productElement.select(".sli_price").get(0).text();
        if (StringUtils.isNotBlank(price)) {
            return Double.parseDouble(StringUtils.removeCurrencySymbols(price));
        }
        return 0D;
    }

    @Override
    protected Double extractSavingFromProductElement(Element productElement) {
        return null;
    }

    protected Double extractOldPriceFromProductElement(Element productElement) {
        Elements oldPriceElements = productElement.select(".sli_orig_price");
        if (oldPriceElements != null && !oldPriceElements.isEmpty()) {
            Element oldPriceElement = oldPriceElements.get(0);
            if (oldPriceElement != null) {
                String price = oldPriceElement.text();
                if (StringUtils.isNotBlank(price)) {
                    return Double.parseDouble(StringUtils.removeCurrencySymbols(price));
                }
                return 0D;
            }
        }
        return null;
    }

}

