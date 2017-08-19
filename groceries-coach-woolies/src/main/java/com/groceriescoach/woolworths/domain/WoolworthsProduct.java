package com.groceriescoach.woolworths.domain;

import com.groceriescoach.core.domain.GroceriesCoachProduct;
import com.groceriescoach.core.domain.GroceriesCoachSortType;
import com.groceriescoach.core.domain.Store;

import static com.groceriescoach.core.domain.Store.Woolworths;

public class WoolworthsProduct extends GroceriesCoachProduct {

    private static final long serialVersionUID = 1441548278080225404L;


    static final class WoolworthsProductBuilder {
        private String name;
        private String description;
        private String imageUrl;
        private String url;
        private Double price;
        private Double wasPrice;
        private String packageSize;
        private Double savingsAmount;
        private Double unitPrice;
        private String unitSize;
        private String unitPriceStr;


        private WoolworthsProductBuilder() {
        }

        public static WoolworthsProductBuilder aWoolworthsProduct() {
            return new WoolworthsProductBuilder();
        }

        public WoolworthsProductBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public WoolworthsProductBuilder withDescription(String description) {
            this.description = description;
            return this;
        }

        public WoolworthsProductBuilder withImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        public WoolworthsProductBuilder withUrl(String url) {
            this.url = url;
            return this;
        }

        public WoolworthsProductBuilder withPrice(Double price) {
            this.price = price;
            return this;
        }

        public WoolworthsProductBuilder withWasPrice(Double wasPrice) {
            this.wasPrice = wasPrice;
            return this;
        }

        public WoolworthsProductBuilder withPackageSize(String packageSize) {
            this.packageSize = packageSize;
            return this;
        }

        public WoolworthsProductBuilder withSavingsAmount(Double savingsAmount) {
            this.savingsAmount = savingsAmount;
            return this;
        }

        public WoolworthsProductBuilder withUnitPrice(Double unitPrice) {
            this.unitPrice = unitPrice;
            return this;
        }

        public WoolworthsProductBuilder withUnitSize(String unitSize) {
            this.unitSize = unitSize;
            return this;
        }

        public WoolworthsProductBuilder withUnitPriceStr(String unitPriceStr) {
            this.unitPriceStr = unitPriceStr;
            return this;
        }

        public WoolworthsProduct build(GroceriesCoachSortType sortType) {
            WoolworthsProduct woolworthsProduct = new WoolworthsProduct();
            woolworthsProduct.preProductElementExtraction(sortType);
            woolworthsProduct.setName(name);
            woolworthsProduct.setDescription(description);
            woolworthsProduct.setImageUrl(imageUrl);
            woolworthsProduct.setUrl(url);
            woolworthsProduct.setPrice(price);
            woolworthsProduct.setWasPrice(wasPrice);
            woolworthsProduct.setPackageSize(packageSize);
            woolworthsProduct.setSaving(savingsAmount);
            woolworthsProduct.setUnitPrice(unitPrice);
            woolworthsProduct.setUnitSize(unitSize);
            woolworthsProduct.setUnitPriceStr(unitPriceStr);
            woolworthsProduct.postProductElementExtraction(sortType);
            return woolworthsProduct;
        }

    }


    @Override
    public Store getStore() {
        return Woolworths;
    }
}
