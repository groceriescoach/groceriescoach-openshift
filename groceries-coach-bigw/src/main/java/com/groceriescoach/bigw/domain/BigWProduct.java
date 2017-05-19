package com.groceriescoach.bigw.domain;

import com.groceriescoach.core.com.groceriescoach.core.utils.StringUtils;
import com.groceriescoach.core.domain.Product;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.groceriescoach.core.domain.Store.Amcal;
import static com.groceriescoach.core.domain.Store.BigW;

public class BigWProduct extends Product {

    public static BigWProduct fromProductElement(Element productElement) {
        BigWProduct product = null;
        product = new BigWProduct();
        product.setName(extractNameFromProductElement(productElement));
        product.setImageUrl(extractImageFromProductElement(productElement));
        product.setUrl(extractUrlFromProductElement(productElement));
        product.setPrice(extractPriceFromProductElement(productElement));
        product.setSaving(extractSavingsFromProductElement(productElement));
        if (product.getSaving() != null && product.getSaving() != 0D) {
            product.setWasPrice(product.getPrice() + product.getSaving());
        }
        product.setStore(BigW);
        return product;
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
            return Double.parseDouble(StringUtils.removeCurrencySymbols(price.substring(1)));
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
                    return Double.parseDouble(StringUtils.removeCurrencySymbols(price.substring(1)));
                }
                return 0D;
            }
        }
        return null;
    }

}

