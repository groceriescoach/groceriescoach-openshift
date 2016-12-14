package com.groceriescoach.core.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.List;

public class Product implements Serializable {


    private String name;
    private String brand;
    private String description;
    private Double price;
    private Double saving;
    private Double wasPrice;
    private String imageUrl;
    private Store store;
    private String packageSize;
    private Double unitPrice;
    private String unitSize;
    private String unitPriceStr;
    private List<QuantityPrice> quantityPriceList;

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
        this.saving = (saving == null ? 0 : saving);
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

    public void setPackageSize(String packageSize) {
        this.packageSize = packageSize;
    }

    public String getPackageSize() {
        return packageSize;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getWasPrice() {
        return wasPrice;
    }

    public void setWasPrice(Double wasPrice) {
        this.wasPrice = wasPrice;
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

    public List<QuantityPrice> getQuantityPriceList() {
        return quantityPriceList;
    }

    public void setQuantityPriceList(List<QuantityPrice> quantityPriceList) {
        this.quantityPriceList = quantityPriceList;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("name", name)
                .append("brand", brand)
                .append("description", description)
                .append("price", price)
                .append("saving", saving)
                .append("wasPrice", wasPrice)
                .append("imageUrl", imageUrl)
                .append("store", store)
                .append("packageSize", packageSize)
                .append("unitPrice", unitPrice)
                .append("unitSize", unitSize)
                .append("unitPriceStr", unitPriceStr)
                .append("quantityPriceList", quantityPriceList)
                .toString();
    }

    public static class QuantityPrice {
        private Integer quantity;
        private Double price;
        private Double unitPrice;
        private String unitSize;
        private String unitPriceStr;


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
    }



}
