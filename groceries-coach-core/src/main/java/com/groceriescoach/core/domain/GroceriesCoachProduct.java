package com.groceriescoach.core.domain;

import com.groceriescoach.core.com.groceriescoach.core.utils.CollectionUtils;
import com.groceriescoach.core.com.groceriescoach.core.utils.StringUtils;
import com.groceriescoach.core.domain.pack.Pack;
import com.groceriescoach.core.domain.pack.PackageCreator;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.groceriescoach.core.com.groceriescoach.core.utils.StringUtils.trimToEmpty;

public abstract class GroceriesCoachProduct implements Serializable {


    private static final long serialVersionUID = -3912433236010197063L;
    private String name;
    private String nameWithoutSpecialCharacters;
    private String brand;
    private String brandWithoutSpecialCharacters;
    private String description;
    private String url;
    private Double price;
    private Double saving;
    private Double wasPrice;
    private String imageUrl;
    private String packageSizeStr;
    private Double packageSize;
    private Double unitPrice;
    private String unitSize;
    private String unitPriceStr;
    private List<QuantityPrice> quantityPriceList = new ArrayList<>();


    public GroceriesCoachProduct() {
    }

    public abstract Store getStore();

    public static List<GroceriesCoachProduct> eliminateProductsWithoutAllSearchKeywords(
            List<GroceriesCoachProduct> allProducts, String keywords) {

        return allProducts.stream()
                .filter(product -> product.containsAllSearchKeywords(keywords))
                .collect(Collectors.toList());
    }

    private boolean containsAllSearchKeywords(String keywords) {
        List<String> searchKeywords =
                Arrays.asList(StringUtils.split(trimToEmpty(StringUtils.removeNonAlphanumericCharacters(keywords))));
        for (String keyword : searchKeywords) {
            String keywordWithoutSpecialCharacters = StringUtils.removeNonAlphanumericCharacters(keyword);
            if (!StringUtils.containsIgnoreCase(nameWithoutSpecialCharacters, keywordWithoutSpecialCharacters) && !StringUtils.containsIgnoreCase(brandWithoutSpecialCharacters, keywordWithoutSpecialCharacters)) {
                return false;
            }
        }
        return true;
    }

    protected void calculateSavings() {
        if (Objects.nonNull(price) && Objects.nonNull(wasPrice) && Objects.isNull(saving)) {
            saving = wasPrice - price;
        }
    }

    protected void calculateOldPrice() {
        if (Objects.isNull(wasPrice) && Objects.nonNull(saving) && saving > 0D) {
            wasPrice = price + saving;
        }
    }

    protected void calculatePackageSize() {

        Pack pack;
        if (StringUtils.isBlank(packageSizeStr) && StringUtils.isBlank(unitPriceStr)) {
            pack = PackageCreator.createPackage(name, price);
        } else if (StringUtils.isNotBlank(unitPriceStr)) {
            pack = PackageCreator.createPackage(packageSizeStr, unitPriceStr, price);
        } else {
            pack = PackageCreator.createPackage(packageSizeStr, price);
        }
        if (pack != null) {
            packageSize = pack.getPackSize();
            packageSizeStr = pack.getPackSizeStr();

            unitPrice = pack.getUnitPrice();
            unitPriceStr = pack.getUnitPriceStr();
            unitSize = pack.getUnitSize();
        }

        if (CollectionUtils.isNotEmpty(quantityPriceList)) {
            pack.updateUnitPricesInQuantityPriceList(quantityPriceList);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = trimToEmpty(name);
        setNameWithoutSpecialCharacters(StringUtils.removeNonAlphanumericCharacters(this.name));
    }

    public String getNameWithoutSpecialCharacters() {
        return nameWithoutSpecialCharacters;
    }

    public void setNameWithoutSpecialCharacters(String nameWithoutSpecialCharacters) {
        this.nameWithoutSpecialCharacters = nameWithoutSpecialCharacters;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = trimToEmpty(brand);
        setBrandWithoutSpecialCharacters(StringUtils.removeNonAlphanumericCharacters(this.brand));
    }

    public String getBrandWithoutSpecialCharacters() {
        return brandWithoutSpecialCharacters;
    }

    public void setBrandWithoutSpecialCharacters(String brandWithoutSpecialCharacters) {
        this.brandWithoutSpecialCharacters = brandWithoutSpecialCharacters;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        Objects.requireNonNull(price, "Price cannot be null");
        this.price = price;
    }

    public Double getSaving() {
        return saving;
    }

    public void setSaving(Double saving) {
        this.saving = saving;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setPackageSizeStr(String packageSizeStr) {
        this.packageSizeStr = packageSizeStr;
    }

    public String getPackageSizeStr() {
        return packageSizeStr;
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

    public void addQuantityPrice(QuantityPrice quantityPrice) {
        if (CollectionUtils.isEmpty(quantityPriceList)) {
            quantityPriceList = new ArrayList<>();
        }
        quantityPriceList.add(quantityPrice);
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
                .append("packageSize", packageSizeStr)
                .append("unitPrice", unitPrice)
                .append("unitSize", unitSize)
                .append("unitPriceStr", unitPriceStr)
                .append("quantityPriceList", quantityPriceList)
                .toString();
    }

    public boolean hasUnitPrice() {
        return unitPrice != null && StringUtils.isNotBlank(unitPriceStr);
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


    protected void preProductElementExtraction(GroceriesCoachSortType sortType) {

    }


    protected void postProductElementExtraction(GroceriesCoachSortType sortType) {

        if (sortType.isUnitPriceRequired()) {
            calculatePackageSize();
        }
        calculateSavings();
        calculateOldPrice();
    }


}
