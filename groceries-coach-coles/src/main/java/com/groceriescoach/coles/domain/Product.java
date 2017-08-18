package com.groceriescoach.coles.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.groceriescoach.core.domain.GroceriesCoachSortType;

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

    @JsonProperty("t")
    private String thumbnail;

    @JsonProperty("pl")
    private int promotionalLimit;

    @JsonProperty("s")
    private String urlSuffix;

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

    public static Collection<ColesProduct> toColesProducts(Product[] products, GroceriesCoachSortType sortType) {
        List<ColesProduct> colesProducts = new ArrayList<>();
        for (Product product : products) {
            colesProducts.add(product.toColesProduct(sortType));
        }
        return colesProducts;
    }

    private ColesProduct toColesProduct(GroceriesCoachSortType sortType) {

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
                .build(sortType);
    }
}
