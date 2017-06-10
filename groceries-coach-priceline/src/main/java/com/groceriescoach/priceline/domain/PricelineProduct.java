package com.groceriescoach.priceline.domain;


import com.groceriescoach.core.com.groceriescoach.core.utils.StringUtils;
import com.groceriescoach.core.domain.GroceriesCoachSortType;
import com.groceriescoach.core.domain.Product;
import com.groceriescoach.core.domain.ProductInformationUnavailableException;
import com.groceriescoach.core.domain.Store;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import static com.groceriescoach.core.domain.Store.Priceline;

public class PricelineProduct extends Product {

    PricelineProduct(Element productElement, GroceriesCoachSortType sortType) throws ProductInformationUnavailableException {
        super(productElement, sortType);
    }

    private static String extractUrlFromProductElement(Element productElement) {
        return productElement.select(".product-image-container a").get(0).attr("href");
    }

    @Override
    protected void extractFromProductElement(Element productElement, GroceriesCoachSortType sortType) {
        setName(extractNameFromProductElement(productElement));
        setBrand(extractBrandFromProductElement(productElement));
        setUrl(extractUrlFromProductElement(productElement));
        setImageUrl(extractImageFromProductElement(productElement));
        setPrice(extractPriceFromProductElement(productElement));
        setWasPrice(extractWasPriceFromProductElement(productElement));
    }

    @Override
    public Store getStore() {
        return Priceline;
    }

    private static Double extractWasPriceFromProductElement(Element productElement) {
        Elements oldPriceElements = productElement.select(".old-price .price");
        if (oldPriceElements.isEmpty()) {
            return null;
        } else {
            return Double.parseDouble(StringUtils.removeCurrencySymbols(oldPriceElements.text()));
        }
    }

    private static String extractBrandFromProductElement(Element productElement) {
        return productElement.select(".product-brand").get(0).text();
    }

    private static String extractNameFromProductElement(Element productElement) {
        return productElement.select(".product-link").get(0).text();
    }

    private static String extractImageFromProductElement(Element productElement) {
        return productElement.select("img").get(0).attr("src");
    }

    private static Double extractPriceFromProductElement(Element productElement) {
        Elements regularPriceElements = productElement.select(".regular-price .price");
        if (regularPriceElements.isEmpty()) {
            return Double.parseDouble(StringUtils.removeCurrencySymbols(productElement.select(".special-price .price").text()));
        } else {
            return Double.parseDouble(StringUtils.removeCurrencySymbols(regularPriceElements.text()));
        }
    }


/*
    public static List<Product> toProducts(PricelineProduct[] pricelineProducts) {

        List<Product> products = new ArrayList<>();
        for (PricelineProduct pricelineProduct : pricelineProducts) {
            Product product = pricelineProduct.toGroceriesCoachProduct();
            if (product != null) {
                products.add(product);
            }
        }
        return products;
    }
*/


}
