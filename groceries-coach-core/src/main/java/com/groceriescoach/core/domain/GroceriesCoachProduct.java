package com.groceriescoach.core.domain;

import com.groceriescoach.core.com.groceriescoach.core.utils.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.groceriescoach.core.com.groceriescoach.core.utils.MathUtils.roundToTwoDecimalPlaces;

public abstract class GroceriesCoachProduct implements Serializable {


    private static final long serialVersionUID = -3912433236010197063L;
    private String name;
    private String brand;
    private String description;
    private String url;
    private Double price;
    private Double saving;
    private Double wasPrice;
    private String imageUrl;
    private String packageSize;
    private Integer packageSizeInt;
    private Double unitPrice;
    private String unitSize;
    private String unitPriceStr;
    private List<QuantityPrice> quantityPriceList = new ArrayList<>();

    public GroceriesCoachProduct() {
    }


    public abstract Store getStore();

    public static List<GroceriesCoachProduct> eliminateProductsWithoutAllSearchKeywords(List<GroceriesCoachProduct> allProducts, String keywords) {
        List<String> searchKeywords = Arrays.asList(StringUtils.split(keywords));
        return allProducts.stream()
                .filter(product -> product.containsAllSearchKeywords(searchKeywords))
                .collect(Collectors.toList());
    }

    private boolean containsAllSearchKeywords(List<String> searchKeywords) {
        for (String keyword : searchKeywords) {
            if (!StringUtils.containsIgnoreCase(name, keyword) && !StringUtils.containsIgnoreCase(brand, keyword)) {
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

        String nameWorkingCopy = StringUtils.trimToEmpty(name).toLowerCase();

        if (!StringUtils.containsIgnoreCase(nameWorkingCopy, "pack") && !StringUtils.containsIgnoreCase(nameWorkingCopy, "pk")) {
            nameWorkingCopy += " pack";
        }

        nameWorkingCopy = nameWorkingCopy.replaceAll("pk", "pack");
        nameWorkingCopy = nameWorkingCopy.replaceAll("-", " ");
        nameWorkingCopy = nameWorkingCopy.replaceAll("mega", " ");
        nameWorkingCopy = nameWorkingCopy.replaceAll("bulk", " ");
        nameWorkingCopy = nameWorkingCopy.replaceAll("pack", " pack ");

        if (StringUtils.containsIgnoreCase(nameWorkingCopy, "pack")) {

            final String[] tokens = StringUtils.split(nameWorkingCopy);
            for (int i = 0; i < tokens.length - 1; i++) {
                if (StringUtils.equalsIgnoreCase("pack", tokens[i + 1])) {
                    try {
                        packageSizeInt = Integer.valueOf(tokens[i]);
                        packageSize = packageSizeInt + " pack";
                        break;
                    } catch (Exception ignored) {

                    }
                }
            }
        }
    }

    protected void calculateUnitPrice() {
        if (packageSizeInt != null && unitPriceHasNotBeenSet()) {
            unitPrice = price / packageSizeInt;
            unitPriceStr = "$" + roundToTwoDecimalPlaces(unitPrice) + " each";
            unitSize = "Each";
        }
    }

    private boolean unitPriceHasNotBeenSet() {
        return (unitPrice == null || unitPrice == 0D);
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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
                .append("packageSize", packageSize)
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

}
