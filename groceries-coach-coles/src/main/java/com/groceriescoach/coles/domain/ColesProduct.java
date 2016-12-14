package com.groceriescoach.coles.domain;

import com.groceriescoach.core.com.groceriescoach.core.utils.CollectionUtils;
import com.groceriescoach.core.com.groceriescoach.core.utils.StringUtils;
import com.groceriescoach.core.domain.Product;
import com.groceriescoach.core.domain.Store;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.groceriescoach.core.com.groceriescoach.core.utils.StringUtils.replaceEncodedCharacters;
import static com.groceriescoach.core.domain.Store.Coles;

public class ColesProduct {


    private String brand;
    private String description;
    private String imageUrl;
    private String name;
    private String packageSize;
    private Double price;
    private String productUrl;
    private Double saving;
    private Store store;
    private Double unitPrice;
    private String unitSize;
    private String unitPriceStr;
    private Double wasPrice;
    private List<QuantityPrice> quantityPriceList = new ArrayList<>();

    private static final Logger logger = LoggerFactory.getLogger(ColesProduct.class);


    public static ColesProduct fromProductElement(Element productElement) {
        ColesProduct colesProduct = new ColesProduct();

        colesProduct.setBrand(extractBrandFromProductElement(productElement));
        colesProduct.setDescription(extractDescriptionFromProductElement(productElement));
        colesProduct.setImageUrl(extractImageUrlFromProductElement(productElement));
        colesProduct.setName(extractNameFromProductElement(productElement));
        colesProduct.setPackageSize(extractPackageSizeFromProductElement(productElement));
        colesProduct.setPrice(extractPriceFromProductElement(productElement));
        colesProduct.setProductUrl(extractProductUrlFromProductElement(productElement));
        colesProduct.setSaving(extractSavingFromProductElement(productElement));
        colesProduct.setStore(Coles);
        colesProduct.setUnitPriceStr(extractUnitPriceFromProductElement(productElement));
        colesProduct.setWasPrice(extractWasPriceFromProductElement(productElement));
        colesProduct.quantityPriceList.addAll(extractQuantityPriceListFromProductElement(productElement));

        return colesProduct;
    }

    private static Collection<? extends QuantityPrice> extractQuantityPriceListFromProductElement(Element productElement) {
        List<QuantityPrice> quantityPriceList = QuantityPrice.extractFromProductElement(productElement);
        return quantityPriceList;
    }

    private static String extractProductUrlFromProductElement(Element productElement) {
        return productElement.select(".product-url").first().attr("href");
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    private static String extractBrandFromProductElement(Element productElement) {
        return productElement.select(".brand").text();
    }

    private static Double extractWasPriceFromProductElement(Element productElement) {
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

    private static String extractDescriptionFromProductElement(Element productElement) {
        return null;
    }

    private static Double extractPriceFromProductElement(Element productElement) {
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

    private static String extractImageUrlFromProductElement(Element productElement) {
        return "http://shop.coles.com.au" + productElement.select(".photo").attr("src");
    }

    private static Double extractSavingFromProductElement(Element productElement) {
        Elements savingElements = productElement.select(".saving");

        if (savingElements.first() != null && savingElements.first().childNodeSize() > 1) {

            TextNode node = (TextNode) savingElements.first().childNodes().get(3).childNode(1);
            return Double.parseDouble(node.text().replaceAll(" save \\$", ""));
        } else {
            return null;
        }
    }

    private static String extractNameFromProductElement(Element productElement) {
        String nameAndSize = productElement.select(".detail .item .product-url").first().html();
        return replaceEncodedCharacters(nameAndSize.split("&nbsp;")[0]);
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getSaving() {
        return saving;
    }

    public void setSaving(Double saving) {
        this.saving = saving;
    }

    public Double getWasPrice() {
        return wasPrice;
    }

    public void setWasPrice(Double wasPrice) {
        this.wasPrice = wasPrice;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public String getPackageSize() {
        return packageSize;
    }

    public void setPackageSize(String packageSize) {
        this.packageSize = packageSize;
    }

    public String getUnitPriceStr() {
        return unitPriceStr;
    }

    public void setUnitPriceStr(String unitPriceStr) {
        this.unitPriceStr = unitPriceStr;

        if (unitPriceStr.contains("per")) {
            String unitPriceElements[] = unitPriceStr.split("per");
            this.unitPrice = Double.parseDouble(StringUtils.removeCurrencySymbols(unitPriceElements[0]));
            this.unitSize = unitPriceElements[1].trim();
        }

    }

    public String getProductUrl() {
        return productUrl;
    }

    public void setProductUrl(String productUrl) {
        this.productUrl = productUrl;
    }


    public Product toGroceriesCoachProduct() {

        Product product = new Product();
        product.setImageUrl(imageUrl);
        product.setSaving(saving);
        product.setWasPrice(wasPrice);
        product.setUnitPrice(unitPrice);
        product.setUnitSize(unitSize);
        product.setUnitPriceStr(unitPriceStr);
        product.setName(name);
        product.setBrand(brand);
        product.setStore(Coles);
        product.setPrice(price);
        product.setPackageSize(packageSize);
        product.setQuantityPriceList(QuantityPrice.toGroceriesCoachQuantityPriceList(quantityPriceList));
        return product;
    }

    private static class QuantityPrice {
        private Integer quantity;
        private Double price;
        private Double unitPrice;
        private String unitSize;
        private String unitPriceStr;

        public QuantityPrice(Element productElement) {

            Elements offerDetailElements = productElement.select(".offer-detail");
            if (!offerDetailElements.isEmpty()) {
                String offerDetails = offerDetailElements.text();
                String offerDetailArray[] = offerDetails.split("for");
                setQuantity(Integer.parseInt(StringUtils.trimToEmpty(offerDetailArray[0])));
                setPrice(Double.parseDouble(StringUtils.removeCurrencySymbols(offerDetailArray[1])));
            }

            Elements offerPriceElements = productElement.select(".offer-price");
            if (!offerPriceElements.isEmpty()) {
                String offerPrice = offerPriceElements.text();
                unitPriceStr = StringUtils.trimToEmpty(offerPrice.split("/")[1]);
                String unitPriceElements[] = unitPriceStr.split("per");
                unitPrice = Double.parseDouble(StringUtils.removeCurrencySymbols(unitPriceElements[0]));
                unitSize = StringUtils.trimToEmpty(unitPriceElements[1]);
            }
        }


        public Integer getQuantity() {
            return quantity;
        }

        public void setQuantity(Integer quantity) {
            this.quantity = quantity;
        }

        public Double getPrice() {
            return price;
        }

        public void setPrice(Double price) {
            this.price = price;
        }

        public Double getUnitPrice() {
            return unitPrice;
        }

        public void setUnitPrice(Double unitPrice) {
            this.unitPrice = unitPrice;
        }

        public String getUnitSize() {
            return unitSize;
        }

        public void setUnitSize(String unitSize) {
            this.unitSize = unitSize;
        }

        public String getUnitPriceStr() {
            return unitPriceStr;
        }

        public void setUnitPriceStr(String unitPriceStr) {
            this.unitPriceStr = unitPriceStr;
        }

        public static List<QuantityPrice> extractFromProductElement(Element productElement) {

            Elements offerDetailElements = productElement.select(".offer-detail");
            List<QuantityPrice> quantityPriceList = new ArrayList<>();
            if (CollectionUtils.isNotEmpty(offerDetailElements) && StringUtils.isNotBlank(offerDetailElements.text())) {
                QuantityPrice quantityPrice = new QuantityPrice(productElement);
                quantityPriceList.add(quantityPrice);
            }
            return quantityPriceList;
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .append("quantity", quantity)
                    .append("price", price)
                    .append("unitPrice", unitPrice)
                    .append("unitSize", unitSize)
                    .append("unitPriceStr", unitPriceStr)
                    .toString();
        }

        public static List<Product.QuantityPrice> toGroceriesCoachQuantityPriceList(List<QuantityPrice> quantityPriceList) {

            if (CollectionUtils.isNotEmpty(quantityPriceList)) {
                List<Product.QuantityPrice> groceriesCoachQuantityPriceList = new ArrayList<>();

                for (QuantityPrice quantityPrice : quantityPriceList) {
                    groceriesCoachQuantityPriceList.add(quantityPrice.toGroceriesCoachQuantityPrice());
                }
                return groceriesCoachQuantityPriceList;
            }
            return null;
        }

        private Product.QuantityPrice toGroceriesCoachQuantityPrice() {

            Product.QuantityPrice quantityPrice = new Product.QuantityPrice();
            quantityPrice.setPrice(price);
            quantityPrice.setQuantity(quantity);
            quantityPrice.setUnitPrice(unitPrice);
            quantityPrice.setUnitPriceStr(unitPriceStr);
            quantityPrice.setUnitSize(unitSize);
            return quantityPrice;
        }
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("brand", brand)
                .append("description", description)
                .append("imageUrl", imageUrl)
                .append("name", name)
                .append("packageSize", packageSize)
                .append("price", price)
                .append("productUrl", productUrl)
                .append("saving", saving)
                .append("store", store)
                .append("unitPrice", unitPrice)
                .append("unitSize", unitSize)
                .append("unitPriceStr", unitPriceStr)
                .append("wasPrice", wasPrice)
                .append("quantityPriceList", quantityPriceList)
                .toString();
    }
}
