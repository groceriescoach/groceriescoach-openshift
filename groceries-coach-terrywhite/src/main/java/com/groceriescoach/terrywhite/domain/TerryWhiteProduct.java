package com.groceriescoach.terrywhite.domain;

import com.groceriescoach.core.com.groceriescoach.core.utils.StringUtils;
import com.groceriescoach.core.domain.GroceriesCoachJsoupProduct;
import com.groceriescoach.core.domain.GroceriesCoachSortType;
import com.groceriescoach.core.domain.ProductInformationUnavailableException;
import com.groceriescoach.core.domain.Store;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import static com.groceriescoach.core.domain.Store.TerryWhite;

public class TerryWhiteProduct extends GroceriesCoachJsoupProduct {

    private static final long serialVersionUID = 4920665614935048907L;

    TerryWhiteProduct(Element productElement, GroceriesCoachSortType sortType) throws ProductInformationUnavailableException {
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
        return TerryWhite;
    }

    @Override
    protected String extractUrlFromProductElement(Element productElement) {
        return productElement.select(".product-title").get(0).attr("href");
    }

    @Override
    protected String extractNameFromProductElement(Element productElement) {
        return productElement.select(".product-title").get(0).attr("title");
    }

    @Override
    protected String extractImageFromProductElement(Element productElement) {
        return productElement.select("img").get(0).attr("src");
    }

    @Override
    protected Double extractPriceFromProductElement(Element productElement) {
        String price = productElement.select(".prod-price").get(0).text();
        if (StringUtils.isNotBlank(price) && price.startsWith("$")) {
            return Double.parseDouble(StringUtils.removeCurrencySymbols(price));
        }
        return 0D;
    }

    @Override
    protected Double extractSavingFromProductElement(Element productElement) {
        final Elements prodSaveElements = productElement.select(".prod-save");
        if (prodSaveElements != null && !prodSaveElements.isEmpty()) {
            String prodSaveText = StringUtils.trimToEmpty(prodSaveElements.get(0).text()).toLowerCase();
            prodSaveText = StringUtils.trimToEmpty(prodSaveText.replaceAll("save", "").replaceAll("off rrp", ""));
            if (StringUtils.isNotBlank(prodSaveText) && prodSaveText.startsWith("$")) {
                return Double.parseDouble(StringUtils.removeCurrencySymbols(prodSaveText));
            }
        }
        return null;
    }

    @Override
    protected Double extractOldPriceFromProductElement(Element productElement) {
        return null;
    }
}

