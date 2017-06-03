package com.groceriescoach.babyvillage.domain;

import com.groceriescoach.core.domain.Product;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

import static com.groceriescoach.core.com.groceriescoach.core.utils.StringUtils.removeCurrencySymbols;
import static com.groceriescoach.core.com.groceriescoach.core.utils.StringUtils.removeThousandSeparators;
import static com.groceriescoach.core.domain.Store.BabyVillage;

public class BabyVillageProduct extends Product {


    static BabyVillageProduct fromProductElement(Document productElement) {
        BabyVillageProduct product = new BabyVillageProduct();

        product.setName(extractNameFromProductElement(productElement));
        product.setImageUrl(extractImageUrlFromProductElement(productElement));
        product.setUrl(extractUrlFromProductElement(productElement));
        product.setWasPrice(extractWasPriceFromProductElement(productElement));
        product.setPrice(extractPriceFromProductElement(productElement));
        product.setSaving(product.calculateSavings());
        product.setStore(BabyVillage);

        return product;
    }

    private static Double extractPriceFromProductElement(Document productElement) {
        final Elements priceElements = productElement.select(".price");
        if (priceElements != null && !priceElements.isEmpty()) {
            final Element priceElement = priceElements.get(0);
            TextNode priceNode = null;
            if (priceElement.childNodeSize() == 1) {
                priceNode = (TextNode) priceElement.childNode(0);
            } else if (priceElement.childNodeSize() == 2) {
                priceNode = (TextNode) priceElement.childNode(1);
            }
            return Double.parseDouble(removeThousandSeparators(removeCurrencySymbols(priceNode.text())));
        }

        return null;
    }

    private static Double extractWasPriceFromProductElement(Document productElement) {
        final Elements rrpElements = productElement.select(".rrp");
        if (rrpElements != null && !rrpElements.isEmpty()) {
            return Double.parseDouble(removeThousandSeparators(removeCurrencySymbols(rrpElements.get(0).text())));
        } else {
            return null;
        }
    }

    private static String extractImageUrlFromProductElement(Document productElement) {
        final Elements imageProductElements = productElement.select(".image");
        if (imageProductElements != null && !imageProductElements.isEmpty()) {
            final String style = imageProductElements.get(0).attr("style");
            return "https://www.babyvillage.com.au" + style.replace("background-image: url('", "").replace("');", "");
        }
        return null;
    }

    private static String extractUrlFromProductElement(Document productElement) {
        Elements productLinkElements = productElement.select("a");
        if (productLinkElements != null && !productLinkElements.isEmpty()) {
            return "https://www.babyvillage.com.au" + productLinkElements.get(0).attr("href");
        } else {
            return "https://www.babyvillage.com.au";
        }
    }

    private static String extractNameFromProductElement(Document productElement) {
        return productElement.select(".name").text();
    }
}

