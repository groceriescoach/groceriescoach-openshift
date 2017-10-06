package com.groceriescoach.royyoung.domain;

import com.groceriescoach.core.com.groceriescoach.core.utils.CurrencyUtils;
import com.groceriescoach.core.com.groceriescoach.core.utils.StringUtils;
import com.groceriescoach.core.domain.GroceriesCoachJsoupProduct;
import com.groceriescoach.core.domain.GroceriesCoachSortType;
import com.groceriescoach.core.domain.ProductInformationUnavailableException;
import com.groceriescoach.core.domain.Store;
import org.jsoup.nodes.Element;

import static com.groceriescoach.core.domain.Store.RoyYoung;

public class RoyYoungProduct extends GroceriesCoachJsoupProduct {
    private static final long serialVersionUID = 840155505450013866L;

    public RoyYoungProduct(Element productElement, GroceriesCoachSortType sortType) throws ProductInformationUnavailableException {
        super(productElement, sortType);
    }

    @Override
    public Store getStore() {
        return RoyYoung;
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
        final Element imageElement = productElement.select("img").get(0);
        String imageSrc = StringUtils.trimToEmpty(imageElement.attr("src"));
        imageSrc = imageSrc.replaceAll("http://royyoungchemist.resultspage.com/thumb.php?f=http%3a%2f%2f", "http://");
        imageSrc = imageSrc.replaceAll("%2f", "/").replaceAll("&amp;x=252&amp;y=252", "");
        return imageSrc;
    }

    @Override
    protected String extractNameFromProductElement(Element productElement) {
        return productElement.select(".product-name").get(0).text();
    }

    @Override
    protected Double extractOldPriceFromProductElement(Element productElement) {
        return null;
    }

    @Override
    protected Double extractPriceFromProductElement(Element productElement) {
        final String auPrice = StringUtils.trimToEmpty(productElement.select(".price-box-hover").get(0).text()).replaceAll("AU", "");
        return CurrencyUtils.extractPriceFrom(auPrice, 0D);
    }

    @Override
    protected Double extractSavingFromProductElement(Element productElement) {
        return null;
    }

    @Override
    protected String extractUrlFromProductElement(Element productElement) {
        return productElement.select(".product-name").get(0).attr("href");
    }
}
