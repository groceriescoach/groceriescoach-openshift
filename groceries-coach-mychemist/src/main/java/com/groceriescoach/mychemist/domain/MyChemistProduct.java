package com.groceriescoach.mychemist.domain;

import com.groceriescoach.core.com.groceriescoach.core.utils.StringUtils;
import com.groceriescoach.core.domain.GroceriesCoachJsoupProduct;
import com.groceriescoach.core.domain.GroceriesCoachSortType;
import com.groceriescoach.core.domain.ProductInformationUnavailableException;
import com.groceriescoach.core.domain.Store;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import static com.groceriescoach.core.domain.Store.MyChemist;

public class MyChemistProduct extends GroceriesCoachJsoupProduct {

    private static final long serialVersionUID = -795877826095347800L;

    public MyChemistProduct(Element productElement, GroceriesCoachSortType sortType) throws ProductInformationUnavailableException {
        super(productElement, sortType);
    }

    @Override
    public Store getStore() {
        return MyChemist;
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
        return "http://www.mychemist.com.au" + productElement.select("img").get(0).attr("src");
    }

    @Override
    protected String extractNameFromProductElement(Element productElement) {
        return productElement.select(".productName_row a").get(0).attr("title");
    }

    @Override
    protected Double extractOldPriceFromProductElement(Element productElement) {
        return null;
    }

    @Override
    protected Double extractPriceFromProductElement(Element productElement) {
        String priceStr = StringUtils.trimToEmpty(productElement.select(".our_price").get(0).text());
        if (StringUtils.isNotBlank(priceStr) && priceStr.startsWith("$")) {
            return Double.parseDouble(StringUtils.removeCurrencySymbols(priceStr));
        } else {
            return 0D;
        }
    }

    @Override
    protected Double extractSavingFromProductElement(Element productElement) {
        Elements savingsElements = productElement.select(".rrp_span");
        if (savingsElements != null && !savingsElements.isEmpty()) {
            String savingsStr = StringUtils.trimToEmpty(savingsElements.get(0).text()).toLowerCase();
            savingsStr = StringUtils.trimToEmpty(savingsStr.replaceAll("save", ""));
            if (StringUtils.isNotBlank(savingsStr) && savingsStr.startsWith("$")) {
                return Double.parseDouble(StringUtils.removeCurrencySymbols(savingsStr));
            }
        }
        return null;
    }

    @Override
    protected String extractUrlFromProductElement(Element productElement) {
        return "http://www.mychemist.com.au/" + productElement.select(".productName_row a").get(0).attr("href");
    }
}
