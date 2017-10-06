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

        static WoolworthsProductBuilder aWoolworthsProduct() {
            return new WoolworthsProductBuilder();
        }

        WoolworthsProductBuilder withName(String name) {
            this.name = name;
            return this;
        }

        WoolworthsProductBuilder withDescription(String description) {
            this.description = description;
            return this;
        }

        WoolworthsProductBuilder withImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        WoolworthsProductBuilder withUrl(String url) {
            this.url = url;
            return this;
        }

        WoolworthsProductBuilder withPrice(Double price) {
            this.price = price;
            return this;
        }

        WoolworthsProductBuilder withWasPrice(Double wasPrice) {
            this.wasPrice = wasPrice;
            return this;
        }

        WoolworthsProductBuilder withPackageSize(String packageSize) {
            this.packageSize = packageSize;
            return this;
        }

        WoolworthsProductBuilder withSavingsAmount(Double savingsAmount) {
            this.savingsAmount = savingsAmount;
            return this;
        }

        WoolworthsProductBuilder withUnitPrice(Double unitPrice) {
            this.unitPrice = unitPrice;
            return this;
        }

        WoolworthsProductBuilder withUnitSize(String unitSize) {
            this.unitSize = unitSize;
            return this;
        }

        WoolworthsProductBuilder withUnitPriceStr(String unitPriceStr) {
            this.unitPriceStr = unitPriceStr;
            return this;
        }

        WoolworthsProduct build(GroceriesCoachSortType sortType) {
            WoolworthsProduct woolworthsProduct = new WoolworthsProduct();
            woolworthsProduct.preProductElementExtraction(sortType);
            woolworthsProduct.setName(name);
            woolworthsProduct.setDescription(description);
            woolworthsProduct.setImageUrl(imageUrl);
            woolworthsProduct.setUrl(url);
            woolworthsProduct.setPrice(price);
            woolworthsProduct.setWasPrice(wasPrice);
            woolworthsProduct.setPackageSizeStr(packageSize);
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
