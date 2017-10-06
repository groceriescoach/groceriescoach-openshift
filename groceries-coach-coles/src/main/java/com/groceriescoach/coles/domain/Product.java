package com.groceriescoach.coles.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.groceriescoach.core.domain.GroceriesCoachSortType;
import com.groceriescoach.core.domain.ProductInformationUnavailableException;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Product implements Serializable {
    private static final long serialVersionUID = 2111759329331853403L;

    @JsonProperty("p")
    private String productCode;

    @JsonProperty("p1")
    private Price price;

    @JsonProperty("a")
    private ProductInfo productInfo;

    @JsonProperty("n")
    private String name;

    @JsonProperty("m")
    private String brand;

    @JsonProperty("u2")
    private String unitPriceStr;

    @JsonProperty("pd")
    private String multiBuyDetails;

    @JsonProperty("t")
    private String thumbnail;

    @JsonProperty("pl")
    private int promotionalLimit;

    @JsonProperty("s")
    private String urlSuffix;

    private static final Logger logger = LoggerFactory.getLogger(Product.class);

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public ProductInfo getProductInfo() {
        return productInfo;
    }

    public void setProductInfo(ProductInfo productInfo) {
        this.productInfo = productInfo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getUnitPriceStr() {
        return unitPriceStr;
    }

    public void setUnitPriceStr(String unitPriceStr) {
        this.unitPriceStr = unitPriceStr;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public int getPromotionalLimit() {
        return promotionalLimit;
    }

    public void setPromotionalLimit(int promotionalLimit) {
        this.promotionalLimit = promotionalLimit;
    }

    public String getUrlSuffix() {
        return urlSuffix;
    }

    public void setUrlSuffix(String urlSuffix) {
        this.urlSuffix = urlSuffix;
    }

    public String getMultiBuyDetails() {
        return multiBuyDetails;
    }

    public void setMultiBuyDetails(String multiBuyDetails) {
        this.multiBuyDetails = multiBuyDetails;
    }

    public static Collection<ColesProduct> toColesProducts(Product[] products, GroceriesCoachSortType sortType) {
        List<ColesProduct> colesProducts = new ArrayList<>();
        for (Product product : products) {
            try {
                colesProducts.add(product.toColesProduct(sortType));
            } catch (ProductInformationUnavailableException e) {
                logger.error("Unable to extract Coles product information from: {}", product);
            }
        }
        return colesProducts;
    }

    private ColesProduct toColesProduct(GroceriesCoachSortType sortType) throws ProductInformationUnavailableException {

        ColesProduct.ColesProductBuilder aColesProduct = ColesProduct.ColesProductBuilder.aColesProduct();

        return aColesProduct
                .withBrand(brand)
                .withImageUrl("https://shop.coles.com.au" + thumbnail)
                .withUrl("https://shop.coles.com.au/a/a-national/product/" + urlSuffix)
                .withName(name)
                .withPrice(price.getNow())
                .withWasPrice(price.getWas())
                .withUnitPriceStr(unitPriceStr)
                .withPackageSize(productInfo.getPackageSize())
                .withMultiBuyDetails(multiBuyDetails)
                .build(sortType);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("productCode", productCode)
                .append("price", price)
                .append("productInfo", productInfo)
                .append("name", name)
                .append("brand", brand)
                .append("unitPriceStr", unitPriceStr)
                .append("thumbnail", thumbnail)
                .append("promotionalLimit", promotionalLimit)
                .append("urlSuffix", urlSuffix)
                .append("multiBuyDetails", multiBuyDetails)
                .toString();
    }
}
