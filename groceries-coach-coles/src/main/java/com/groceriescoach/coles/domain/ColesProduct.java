package com.groceriescoach.coles.domain;

import com.groceriescoach.core.com.groceriescoach.core.utils.CollectionUtils;
import com.groceriescoach.core.com.groceriescoach.core.utils.StringUtils;
import com.groceriescoach.core.domain.GroceriesCoachProduct;
import com.groceriescoach.core.domain.GroceriesCoachSortType;
import com.groceriescoach.core.domain.Store;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static com.groceriescoach.core.domain.Store.Coles;

public class ColesProduct extends GroceriesCoachProduct {

    private static final long serialVersionUID = -1185239086449021441L;

    private static final Logger logger = LoggerFactory.getLogger(ColesProduct.class);



    public static final class ColesProductBuilder {
        private String brand;
        private String name;
        private Double price;
        private Double wasPrice;
        private String imageUrl;
        private String url;
        private String unitPriceStr;
        private String packageSize;

        private ColesProductBuilder() {
        }

        public static ColesProductBuilder aColesProduct() {
            return new ColesProductBuilder();
        }

        public ColesProductBuilder withBrand(String brand) {
            this.brand = brand;
            return this;
        }

        public ColesProductBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public ColesProductBuilder withPrice(Double price) {
            this.price = price;
            return this;
        }

        public ColesProductBuilder withWasPrice(Double wasPrice) {
            this.wasPrice = wasPrice;
            return this;
        }

        public ColesProductBuilder withImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        public ColesProductBuilder withUrl(String url) {
            this.url = url;
            return this;
        }

        public ColesProductBuilder withUnitPriceStr(String unitPriceStr) {
            this.unitPriceStr = unitPriceStr;
            return this;
        }

        public ColesProductBuilder withPackageSize(String packageSize) {
            this.packageSize = packageSize;
            return this;
        }

        public ColesProduct build(GroceriesCoachSortType sortType) {
            ColesProduct colesProduct = new ColesProduct();
            colesProduct.preProductElementExtraction(sortType);
            colesProduct.setBrand(brand);
            colesProduct.setName(name);
            colesProduct.setPrice(price);
            colesProduct.setUnitPriceStr(unitPriceStr);
            colesProduct.setPackageSize(packageSize);
            colesProduct.setImageUrl(imageUrl);
            colesProduct.postProductElementExtraction(sortType);
            return colesProduct;
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

    protected String extractImageFromProductElement(Element productElement) {
        return "http://shop.coles.com.au" + productElement.select(".photo").attr("src");
    }

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
