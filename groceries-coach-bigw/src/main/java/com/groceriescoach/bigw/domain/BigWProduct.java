package com.groceriescoach.bigw.domain;

import com.groceriescoach.core.com.groceriescoach.core.utils.StringUtils;
import com.groceriescoach.core.domain.GroceriesCoachJsoupProduct;
import com.groceriescoach.core.domain.GroceriesCoachSortType;
import com.groceriescoach.core.domain.ProductInformationUnavailableException;
import com.groceriescoach.core.domain.Store;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import static com.groceriescoach.core.domain.Store.BigW;

public class BigWProduct extends GroceriesCoachJsoupProduct {

    BigWProduct(Element productElement, GroceriesCoachSortType sortType) throws ProductInformationUnavailableException {
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
        return BigW;
    }

    protected String extractUrlFromProductElement(Element productElement) {
        return "https://www.bigw.com.au" + productElement.select(".details .productMainLink").get(0).attr("href");
    }

    protected String extractNameFromProductElement(Element productElement) {
        return productElement.select(".details a").get(0).text();
    }

    @Override
    protected Double extractOldPriceFromProductElement(Element productElement) {
        return null;
    }

    protected String extractImageFromProductElement(Element productElement) {
        return "https://www.bigw.com.au" + productElement.select(".delayed-image-load").get(0).attr("data-src").replaceAll("\\{width\\}", "product");
    }

    protected Double extractPriceFromProductElement(Element productElement) {
        String price = productElement.select(".price strong").get(0).text();
        if (StringUtils.isNotBlank(price) && price.startsWith("$")) {
            return Double.parseDouble(StringUtils.removeCurrencySymbols(price));
        }
        return 0D;
    }

    @Override
    protected Double extractSavingFromProductElement(Element productElement) {
        Elements saveElements = productElement.select(".save");
        if (saveElements != null && !saveElements.isEmpty()) {
            Element saveElement = saveElements.get(0);
            String savingText = saveElement.text();
            if (StringUtils.isNotBlank(savingText)) {
                savingText = savingText.replaceAll("Save ", "");
                savingText = StringUtils.removeCurrencySymbols(savingText);
                return Double.parseDouble(savingText);
            }
        }
        return 0D;
    }

}

