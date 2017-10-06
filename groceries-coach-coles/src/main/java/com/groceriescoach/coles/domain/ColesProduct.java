package com.groceriescoach.coles.domain;

import com.groceriescoach.core.com.groceriescoach.core.utils.CurrencyUtils;
import com.groceriescoach.core.com.groceriescoach.core.utils.StringUtils;
import com.groceriescoach.core.domain.GroceriesCoachProduct;
import com.groceriescoach.core.domain.GroceriesCoachSortType;
import com.groceriescoach.core.domain.ProductInformationUnavailableException;
import com.groceriescoach.core.domain.Store;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.groceriescoach.core.domain.Store.Coles;

public class ColesProduct extends GroceriesCoachProduct {

    private static final long serialVersionUID = -1185239086449021441L;

    private static final Logger logger = LoggerFactory.getLogger(ColesProduct.class);


    static final class ColesProductBuilder {
        private String brand;
        private String name;
        private Double price;
        private Double wasPrice;
        private String imageUrl;
        private String url;
        private String unitPriceStr;
        private String packageSize;
        private String multiBuyDetails;

        private ColesProductBuilder() {
        }

        static ColesProductBuilder aColesProduct() {
            return new ColesProductBuilder();
        }

        ColesProductBuilder withBrand(String brand) {
            this.brand = brand;
            return this;
        }

        ColesProductBuilder withName(String name) {
            this.name = name;
            return this;
        }

        ColesProductBuilder withPrice(Double price) {
            this.price = price;
            return this;
        }

        ColesProductBuilder withWasPrice(Double wasPrice) {
            this.wasPrice = wasPrice;
            return this;
        }

        ColesProductBuilder withImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        ColesProductBuilder withUrl(String url) {
            this.url = url;
            return this;
        }

        ColesProductBuilder withUnitPriceStr(String unitPriceStr) {
            this.unitPriceStr = unitPriceStr;
            return this;
        }

        ColesProductBuilder withPackageSize(String packageSize) {
            this.packageSize = packageSize;
            return this;
        }

        ColesProductBuilder withMultiBuyDetails(String multiBuyDetails) {
            this.multiBuyDetails = multiBuyDetails;
            return this;
        }

        ColesProduct build(GroceriesCoachSortType sortType) throws ProductInformationUnavailableException {
            try {
                ColesProduct colesProduct = new ColesProduct();
                colesProduct.preProductElementExtraction(sortType);
                colesProduct.setBrand(brand);
                colesProduct.setName(name);
                colesProduct.setPrice(price);
                colesProduct.setWasPrice(wasPrice);
                colesProduct.setUnitPriceStr(unitPriceStr);
                colesProduct.setPackageSizeStr(packageSize);
                colesProduct.setImageUrl(imageUrl);
                colesProduct.setUrl(url);
                if (StringUtils.isNotBlank(multiBuyDetails)) {
                    colesProduct.addQuantityPrice(createQuantityPrice());
                }
                colesProduct.postProductElementExtraction(sortType);
                return colesProduct;
            } catch (Exception e) {
                logger.debug("Unable to extract Coles Product", e);
                throw new ProductInformationUnavailableException();
            }
        }

        private QuantityPrice createQuantityPrice() {
            QuantityPrice quantityPrice = new QuantityPrice();

            final String[] multiBuyDetailsElements = StringUtils.split(multiBuyDetails);
            if (ArrayUtils.isNotEmpty(multiBuyDetailsElements) && multiBuyDetailsElements.length == 4) {
                quantityPrice.setQuantity(Integer.parseInt(multiBuyDetailsElements[1]));
                quantityPrice.setPrice(CurrencyUtils.extractPriceFrom(multiBuyDetailsElements[3], null));
            }
            return quantityPrice;
        }
    }

    @Override
    public Store getStore() {
        return Coles;
    }

    public void setUnitPriceStr(String unitPriceStr) {
        unitPriceStr = StringUtils.trimToEmpty(unitPriceStr);
        super.setUnitPriceStr(unitPriceStr);

        if (unitPriceStr.contains("per")) {
            String unitPriceElements[] = unitPriceStr.split("per");
            setUnitPrice(CurrencyUtils.extractPriceFrom(unitPriceElements[0], 0D));
            setUnitSize(unitPriceElements[1].trim());
        }
    }

}
