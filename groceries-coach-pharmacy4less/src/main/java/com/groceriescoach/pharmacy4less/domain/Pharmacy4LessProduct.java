package com.groceriescoach.pharmacy4less.domain;

import com.groceriescoach.core.domain.GroceriesCoachProduct;
import com.groceriescoach.core.domain.GroceriesCoachSortType;
import com.groceriescoach.core.domain.Store;

import static com.groceriescoach.core.domain.Store.Pharmacy4Less;

public class Pharmacy4LessProduct extends GroceriesCoachProduct {

    private static final long serialVersionUID = 1760144659238592523L;

    public Store getStore() {
        return Pharmacy4Less;
    }


    public static final class Pharmacy4LessProductBuilder {
        private String name;
        private String url;
        private Double price;
        private String imageUrl;

        private Pharmacy4LessProductBuilder() {
        }

        public static Pharmacy4LessProductBuilder aPharmacy4LessProduct() {
            return new Pharmacy4LessProductBuilder();
        }

        public Pharmacy4LessProductBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public Pharmacy4LessProductBuilder withUrl(String url) {
            this.url = url;
            return this;
        }

        public Pharmacy4LessProductBuilder withPrice(Double price) {
            this.price = price;
            return this;
        }

        public Pharmacy4LessProductBuilder withImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        public Pharmacy4LessProduct build(GroceriesCoachSortType sortType) {
            Pharmacy4LessProduct pharmacy4LessProduct = new Pharmacy4LessProduct();
            pharmacy4LessProduct.setName(name);
            pharmacy4LessProduct.setUrl(url);
            pharmacy4LessProduct.setPrice(price);
            pharmacy4LessProduct.setImageUrl(imageUrl);
            pharmacy4LessProduct.postProductElementExtraction(sortType);
            return pharmacy4LessProduct;
        }
    }
}

