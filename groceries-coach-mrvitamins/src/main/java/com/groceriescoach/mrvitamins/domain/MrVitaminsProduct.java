package com.groceriescoach.mrvitamins.domain;

import com.groceriescoach.core.domain.GroceriesCoachProduct;
import com.groceriescoach.core.domain.GroceriesCoachSortType;
import com.groceriescoach.core.domain.Store;

import static com.groceriescoach.core.domain.Store.MrVitamins;

public class MrVitaminsProduct extends GroceriesCoachProduct {


    private static final long serialVersionUID = -8331289327652739035L;

    @Override
    public Store getStore() {
        return MrVitamins;
    }


    public static final class MrVitaminsProductBuilder {

        private String name;
        private String description;
        private String url;
        private Double price;
        private Double wasPrice;
        private String imageUrl;

        private MrVitaminsProductBuilder() {
        }

        public static MrVitaminsProductBuilder aMrVitaminsProduct() {
            return new MrVitaminsProductBuilder();
        }

        public MrVitaminsProductBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public MrVitaminsProductBuilder withDescription(String description) {
            this.description = description;
            return this;
        }

        public MrVitaminsProductBuilder withUrl(String url) {
            this.url = url;
            return this;
        }

        public MrVitaminsProductBuilder withPrice(Double price) {
            this.price = price;
            return this;
        }

        public MrVitaminsProductBuilder withWasPrice(Double wasPrice) {
            this.wasPrice = wasPrice;
            return this;
        }

        public MrVitaminsProductBuilder withImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        public MrVitaminsProduct build(GroceriesCoachSortType sortType) {
            MrVitaminsProduct mrVitaminsProduct = new MrVitaminsProduct();
            mrVitaminsProduct.setName(name);
            mrVitaminsProduct.setDescription(description);
            mrVitaminsProduct.setUrl(url);
            mrVitaminsProduct.setPrice(price);
            mrVitaminsProduct.setWasPrice(wasPrice);
            mrVitaminsProduct.setImageUrl(imageUrl);
            mrVitaminsProduct.postProductElementExtraction(sortType);
            return mrVitaminsProduct;
        }
    }
}
