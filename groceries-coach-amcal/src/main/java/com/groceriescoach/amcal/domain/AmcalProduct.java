package com.groceriescoach.amcal.domain;

import com.groceriescoach.core.com.groceriescoach.core.utils.StringUtils;
import com.groceriescoach.core.domain.GroceriesCoachJsoupProduct;
import com.groceriescoach.core.domain.GroceriesCoachSortType;
import com.groceriescoach.core.domain.ProductInformationUnavailableException;
import com.groceriescoach.core.domain.Store;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import static com.groceriescoach.core.domain.Store.Amcal;

public class AmcalProduct extends GroceriesCoachJsoupProduct {

    private static final long serialVersionUID = -401791345740735643L;

    AmcalProduct(Element productElement, GroceriesCoachSortType sortType) throws ProductInformationUnavailableException {
        super(productElement, sortType);
    }

    @Override
    protected void extractFromProductElement(Element productElement, GroceriesCoachSortType sortType)
            throws ProductInformationUnavailableException {

        if (!productElement.getElementsByClass("product_info").isEmpty()) {
            super.extractFromProductElement(productElement, sortType);
        } else {
            throw new ProductInformationUnavailableException();
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
    public Store getStore() {
        return Amcal;
    }

    @Override
    protected String extractUrlFromProductElement(Element productElement) {
        return productElement.select(".product_name a").get(0).attr("href");
    }

    @Override
    protected String extractNameFromProductElement(Element productElement) {
        return productElement.select(".product_name a").get(0).text();
    }

    @Override
    protected String extractImageFromProductElement(Element productElement) {
        return "https://www.amcal.com.au" + productElement.select("img").get(0).attr("src");
    }

    @Override
    protected Double extractPriceFromProductElement(Element productElement) {
        String price = productElement.select(".price").get(0).text();
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
        Elements oldPriceElements = productElement.select(".old_price");
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

