package com.groceriescoach.bigw.domain;

import com.groceriescoach.core.com.groceriescoach.core.utils.StringUtils;
import com.groceriescoach.core.domain.GroceriesCoachSortType;
import com.groceriescoach.core.domain.Product;
import com.groceriescoach.core.domain.ProductInformationUnavailableException;
import com.groceriescoach.core.domain.Store;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import static com.groceriescoach.core.domain.Store.BigW;

public class BigWProduct extends Product {

    BigWProduct(Element productElement, GroceriesCoachSortType sortType) throws ProductInformationUnavailableException {
        super(productElement, sortType);
    }

    @Override
    protected void extractFromProductElement(Element productElement, GroceriesCoachSortType sortType) {
        setName(extractNameFromProductElement(productElement));
        setImageUrl(extractImageFromProductElement(productElement));
        setUrl(extractUrlFromProductElement(productElement));
        setPrice(extractPriceFromProductElement(productElement));
        setSaving(extractSavingsFromProductElement(productElement));
        if (getSaving() != null && getSaving() != 0D) {
            setWasPrice(getPrice() + getSaving());
        }
    }

    @Override
    public Store getStore() {
        return BigW;
    }

    private static Double extractSavingsFromProductElement(Element productElement) {
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

    private static String extractUrlFromProductElement(Element productElement) {
        return "https://www.bigw.com.au" + productElement.select(".details .productMainLink").get(0).attr("href");
    }

    private static String extractNameFromProductElement(Element productElement) {
        return productElement.select(".details a").get(0).text();
    }

    private static String extractImageFromProductElement(Element productElement) {
        return "https://www.bigw.com.au" + productElement.select(".delayed-image-load").get(0).attr("data-src").replaceAll("\\{width\\}", "product");
    }

    private static Double extractPriceFromProductElement(Element productElement) {
        String price = productElement.select(".price strong").get(0).text();
        if (StringUtils.isNotBlank(price) && price.startsWith("$")) {
            return Double.parseDouble(StringUtils.removeCurrencySymbols(price));
        }
        return 0D;
    }

}

