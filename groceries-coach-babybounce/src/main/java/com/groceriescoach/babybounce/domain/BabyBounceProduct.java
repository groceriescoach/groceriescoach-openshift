package com.groceriescoach.babybounce.domain;

import com.groceriescoach.core.com.groceriescoach.core.utils.StringUtils;
import com.groceriescoach.core.domain.GroceriesCoachJsoupProduct;
import com.groceriescoach.core.domain.GroceriesCoachSortType;
import com.groceriescoach.core.domain.ProductInformationUnavailableException;
import com.groceriescoach.core.domain.Store;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import static com.groceriescoach.core.domain.Store.BabyBounce;

public class BabyBounceProduct extends GroceriesCoachJsoupProduct {


    BabyBounceProduct(Element productElement, GroceriesCoachSortType sortType) throws ProductInformationUnavailableException {
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
        return BabyBounce;
    }


    @Override
    protected String extractUrlFromProductElement(Element productElement) {
        return productElement.select(".product-name").get(0).attr("href");
    }

    @Override
    protected String extractNameFromProductElement(Element productElement) {
        return productElement.select(".product-name").get(0).text();
    }

    @Override
    protected String extractImageFromProductElement(Element productElement) {
        return productElement.select(".primary_img img").get(0).attr("src");
    }

    @Override
    protected Double extractPriceFromProductElement(Element productElement) {
        String price = "";
        Elements specialPriceElements = productElement.select(".special-price .price");
        if (specialPriceElements == null || specialPriceElements.isEmpty()) {
            specialPriceElements = productElement.select(".regular-price .price");
        }
        if (specialPriceElements == null || specialPriceElements.isEmpty()) {
            specialPriceElements = productElement.select(".item-price .price");
        }

        price = specialPriceElements.get(0).text();

        if (StringUtils.isNotBlank(price) && price.startsWith("$")) {
            return Double.parseDouble(StringUtils.removeCurrencySymbols(price));
        }
        return 0D;
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
                if (StringUtils.isNotBlank(price) && price.startsWith("$")) {
                    return Double.parseDouble(StringUtils.removeCurrencySymbols(price));
                }
                return 0D;
            }
        }
        return null;
    }

}

