package com.groceriescoach.coles.domain;

import com.groceriescoach.core.com.groceriescoach.core.utils.CollectionUtils;
import com.groceriescoach.core.com.groceriescoach.core.utils.StringUtils;
import com.groceriescoach.core.domain.GroceriesCoachJsoupProduct;
import com.groceriescoach.core.domain.GroceriesCoachSortType;
import com.groceriescoach.core.domain.ProductInformationUnavailableException;
import com.groceriescoach.core.domain.Store;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static com.groceriescoach.core.com.groceriescoach.core.utils.StringUtils.replaceEncodedCharacters;
import static com.groceriescoach.core.domain.Store.Coles;

public class ColesProduct extends GroceriesCoachJsoupProduct {


    private static final Logger logger = LoggerFactory.getLogger(ColesProduct.class);

    ColesProduct(Element productElement, GroceriesCoachSortType sortType) throws ProductInformationUnavailableException {
        super(productElement, sortType);
    }

    @Override
    protected String extractUrlFromProductElement(Element productElement) {
        return productElement.select(".product-url").first().attr("href");
    }

    @Override
    protected String extractBrandFromProductElement(Element productElement) {
        return productElement.select(".brand").text();
    }

    @Override
    protected Double extractOldPriceFromProductElement(Element productElement) {
        Elements savingElements = productElement.select(".saving");
        if (!savingElements.isEmpty() && savingElements.first().childNodeSize() > 1) {
            TextNode wasTextNode = (TextNode) savingElements.first().childNodes().get(2);
            String wasPriceText = wasTextNode.text();
            return Double.parseDouble(wasPriceText.trim().replaceAll("was \\$", ""));
        } else {
            return null;
        }
    }

    private static String extractUnitPriceFromProductElement(Element productElement) {
        Elements unitPriceElements = productElement.select(".unit-price");
        if (unitPriceElements.isEmpty()) {
            Elements standardPriceElements = productElement.select(".std-price");
            String standardPrice = standardPriceElements.first().text();
            return StringUtils.trimToEmpty(standardPrice.split("/")[1]);
        } else {
            List<TextNode> textNodes = unitPriceElements.get(0).textNodes();
            for (TextNode textNode : textNodes) {
                if (StringUtils.isNotBlank(textNode.text())) {
                    return StringUtils.trimToEmpty(textNode.text());
                }
            }
            return "";
        }
    }

    private static String extractPackageSizeFromProductElement(Element productElement) {
        String nameAndSize = productElement.select(".detail .item .product-url").first().html();
        return nameAndSize.split("&nbsp;")[1].trim();
    }

    @Override
    protected String extractDescriptionFromProductElement(Element productElement) {
        return null;
    }

    @Override
    protected Double extractPriceFromProductElement(Element productElement) {
        Double price = 0D;

        Elements priceElements = productElement.select(".purchasing .price");

        if (priceElements.isEmpty()) {
            String standardAndUnitPriceStr = productElement.select(".purchasing .std-price").text();

            String standardPriceStr = "";
            String unitPriceStr = "";

            if (standardAndUnitPriceStr.contains("/")) {
                String standardAndUnitPriceElements[] = standardAndUnitPriceStr.split("/");
                standardPriceStr = StringUtils.trimToEmpty(standardAndUnitPriceElements[0]);
                unitPriceStr = StringUtils.trimToEmpty(standardAndUnitPriceElements[1]);
            } else {
                standardPriceStr = StringUtils.trimToEmpty(standardAndUnitPriceStr);
                unitPriceStr = "";
            }

            if (StringUtils.isNotBlank(standardPriceStr)) {
                price = Double.parseDouble(StringUtils.removeCurrencySymbols(standardPriceStr.split("for")[1]));
            }

        } else {

            Element purchasingPriceElement = priceElements.get(0);
            List<TextNode> textNodes = purchasingPriceElement.textNodes();
            for (TextNode textNode : textNodes) {
                if (StringUtils.isNotBlank(textNode.text())) {
                    price = Double.parseDouble(textNode.text());
                    break;
                }
            }
        }

        return price;
    }

    @Override
    protected String extractImageFromProductElement(Element productElement) {
        return "http://shop.coles.com.au" + productElement.select(".photo").attr("src");
    }

    @Override
    protected Double extractSavingFromProductElement(Element productElement) {
        Elements savingElements = productElement.select(".saving");

        if (savingElements.first() != null && savingElements.first().childNodeSize() > 1) {

            TextNode node = (TextNode) savingElements.first().childNodes().get(3).childNode(1);
            return Double.parseDouble(node.text().replaceAll(" save \\$", ""));
        } else {
            return null;
        }
    }

    @Override
    protected String extractNameFromProductElement(Element productElement) {
        String nameAndSize = productElement.select(".detail .item .product-url").first().html();
        return replaceEncodedCharacters(nameAndSize.split("&nbsp;")[0]);
    }

    @Override
    protected void extractFromProductElement(Element productElement, GroceriesCoachSortType sortType) {
        setBrand(extractBrandFromProductElement(productElement));
        setDescription(extractDescriptionFromProductElement(productElement));
        setImageUrl(extractImageFromProductElement(productElement));
        setName(extractNameFromProductElement(productElement));
        setPackageSize(extractPackageSizeFromProductElement(productElement));
        setPrice(extractPriceFromProductElement(productElement));
        setUrl(extractUrlFromProductElement(productElement));
        setSaving(extractSavingFromProductElement(productElement));
        setUnitPriceStr(extractUnitPriceFromProductElement(productElement));
        setWasPrice(extractOldPriceFromProductElement(productElement));
        getQuantityPriceList().addAll(extractQuantityPriceListFromProductElement(productElement));
    }

    @Override
    public Store getStore() {
        return Coles;
    }

    public void setUnitPriceStr(String unitPriceStr) {
        super.setUnitPriceStr(unitPriceStr);

        if (unitPriceStr.contains("per")) {
            String unitPriceElements[] = unitPriceStr.split("per");
            setUnitPrice(Double.parseDouble(StringUtils.removeCurrencySymbols(unitPriceElements[0])));
            setUnitSize(unitPriceElements[1].trim());
        }
    }

    public static List<QuantityPrice> extractQuantityPriceListFromProductElement(Element productElement) {

        Elements offerDetailElements = productElement.select(".offer-detail");
        List<QuantityPrice> quantityPriceList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(offerDetailElements) && StringUtils.isNotBlank(offerDetailElements.text())) {
            QuantityPrice quantityPrice = extractQuantityPriceFromProductElement(productElement);
            quantityPriceList.add(quantityPrice);
        }
        return quantityPriceList;
    }

    public static QuantityPrice extractQuantityPriceFromProductElement (Element productElement) {

        QuantityPrice quantityPrice = new QuantityPrice();

        Elements offerDetailElements = productElement.select(".offer-detail");
        if (!offerDetailElements.isEmpty()) {
            String offerDetails = offerDetailElements.text();
            String offerDetailArray[] = offerDetails.split("for");
            quantityPrice.setQuantity(Integer.parseInt(StringUtils.trimToEmpty(offerDetailArray[0])));
            quantityPrice.setPrice(Double.parseDouble(StringUtils.removeCurrencySymbols(offerDetailArray[1])));
        }

        Elements offerPriceElements = productElement.select(".offer-price");
        if (!offerPriceElements.isEmpty()) {
            String offerPrice = offerPriceElements.text();
            quantityPrice.setUnitPriceStr(StringUtils.trimToEmpty(offerPrice.split("/")[1]));
            String unitPriceElements[] = quantityPrice.getUnitPriceStr().split("per");
            quantityPrice.setUnitPrice(Double.parseDouble(StringUtils.removeCurrencySymbols(unitPriceElements[0])));
            quantityPrice.setUnitSize(StringUtils.trimToEmpty(unitPriceElements[1]));
        }

        return quantityPrice;
    }

}
