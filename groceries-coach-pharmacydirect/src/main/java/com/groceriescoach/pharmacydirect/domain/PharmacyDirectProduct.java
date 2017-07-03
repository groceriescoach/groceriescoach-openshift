package com.groceriescoach.pharmacydirect.domain;

import com.groceriescoach.core.com.groceriescoach.core.utils.StringUtils;
import com.groceriescoach.core.domain.GroceriesCoachJsoupProduct;
import com.groceriescoach.core.domain.GroceriesCoachSortType;
import com.groceriescoach.core.domain.ProductInformationUnavailableException;
import com.groceriescoach.core.domain.Store;
import org.jsoup.nodes.Element;

import static com.groceriescoach.core.domain.Store.PharmacyDirect;

public class PharmacyDirectProduct extends GroceriesCoachJsoupProduct {

    private static final long serialVersionUID = 877501780759670540L;

    public PharmacyDirectProduct(Element productElement, GroceriesCoachSortType sortType) throws ProductInformationUnavailableException {
        super(productElement, sortType);
    }

    @Override
    public Store getStore() {
        return PharmacyDirect;
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
        return productElement.select(".sli_grid_image img").get(0).attr("src");
    }

    @Override
    protected String extractNameFromProductElement(Element productElement) {
        return productElement.select(".sli_grid_title").get(0).text();
    }

    @Override
    protected Double extractOldPriceFromProductElement(Element productElement) {
        return null;
    }

    @Override
    protected Double extractPriceFromProductElement(Element productElement) {
        String price = productElement.select(".sli_grid_price .sli_grid_price").get(0).text();
        return Double.parseDouble(StringUtils.removeCurrencySymbols(price));
    }

    @Override
    protected Double extractSavingFromProductElement(Element productElement) {
        return null;
    }

    @Override
    protected String extractUrlFromProductElement(Element productElement) {
        return productElement.select(".sli_grid_title a").get(0).attr("href");
    }
}
