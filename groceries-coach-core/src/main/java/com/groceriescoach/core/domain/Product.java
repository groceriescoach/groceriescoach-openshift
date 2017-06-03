package com.groceriescoach.core.domain;

import com.groceriescoach.core.com.groceriescoach.core.utils.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Product implements Serializable {


    private String name;
    private String brand;
    private String description;
    private String url;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public static List<Product> eliminateProductsWithoutAllSearchKeywords(List<Product> allProducts, String keywords) {
        List<String> searchKeywords = Arrays.asList(StringUtils.split(keywords));
        List<Product> filteredProducts =
                allProducts.stream()
                .filter(product -> product.containsAllSearchKeywords(searchKeywords))
                .collect(Collectors.toList());
        return filteredProducts;
    }

    private boolean containsAllSearchKeywords(List<String> searchKeywords) {
        for (String keyword: searchKeywords) {
            if (!StringUtils.containsIgnoreCase(name, keyword) && !StringUtils.containsIgnoreCase(brand, keyword)) {
                return false;
            }
        }
        return true;
    }

    protected Double calculateSavings() {
        if (Objects.nonNull(price) && Objects.nonNull(wasPrice)) {
            return wasPrice - price;
        } else {
            return null;
        }
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


        private void calculateUnitPrice() {

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
