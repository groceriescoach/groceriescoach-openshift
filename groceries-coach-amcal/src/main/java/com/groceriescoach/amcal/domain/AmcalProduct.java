package com.groceriescoach.amcal.domain;

import com.groceriescoach.core.com.groceriescoach.core.utils.StringUtils;
import com.groceriescoach.core.domain.GroceriesCoachSortType;
import com.groceriescoach.core.domain.Product;
import com.groceriescoach.core.domain.ProductInformationUnavailableException;
import com.groceriescoach.core.domain.Store;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import static com.groceriescoach.core.domain.Store.Amcal;

public class AmcalProduct extends Product {

    AmcalProduct(Element productElement, GroceriesCoachSortType sortType) throws ProductInformationUnavailableException {
        super(productElement, sortType);
    }

    @Override
    protected void extractFromProductElement(Element productElement, GroceriesCoachSortType sortType) throws ProductInformationUnavailableException {
        if (!productElement.getElementsByClass("product_info").isEmpty()) {
            setName(extractNameFromProductElement(productElement));
            setImageUrl(extractImageFromProductElement(productElement));
            setUrl(extractUrlFromProductElement(productElement));
            setPrice(extractPriceFromProductElement(productElement));
            setWasPrice(extractOldPriceFromProductElement(productElement));
        } else {
            throw new ProductInformationUnavailableException();
        }

    }

    @Override
    public Store getStore() {
        return Amcal;
    }

    private static String extractUrlFromProductElement(Element productElement) {
        return productElement.select(".product_name a").get(0).attr("href");
    }

    private static String extractNameFromProductElement(Element productElement) {
        return productElement.select(".product_name a").get(0).text();
    }

    private static String extractImageFromProductElement(Element productElement) {
        return "https://www.amcal.com.au" + productElement.select("img").get(0).attr("src");
    }

    private static Double extractPriceFromProductElement(Element productElement) {
        String price = productElement.select(".price").get(0).text();
        if (StringUtils.isNotBlank(price) && price.startsWith("$")) {
            return Double.parseDouble(StringUtils.removeCurrencySymbols(price));
        }
        return 0D;
    }

    public static Double extractOldPriceFromProductElement(Element productElement) {
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

