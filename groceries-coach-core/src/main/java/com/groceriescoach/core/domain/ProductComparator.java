package com.groceriescoach.core.domain;

import com.groceriescoach.core.com.groceriescoach.core.utils.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Comparator;

public class ProductComparator implements Comparator<Product> {

    private GroceriesCoachSortType sortType;

    private static final Logger logger = LoggerFactory.getLogger(ProductComparator.class);

    public ProductComparator(GroceriesCoachSortType sortType) {
        this.sortType = sortType;
    }

    @Override
    public int compare(Product product1, Product product2) {

        logger.debug("Comparing {} and {}", product1, product2);

        switch (sortType) {
            case A_TO_Z_ProductName:
                return product1.getName().compareTo(product2.getName());
            case PriceLowToHigh:
                return product1.getPrice().compareTo(product2.getPrice());
            case UnitPriceLowToHigh:
                Double product1UnitPrice = product1.getUnitPrice();
                Double product2UnitPrice = product2.getUnitPrice();
                if (product1UnitPrice == null && product2UnitPrice == null) {
                    return 0;
                } else if (product2UnitPrice == null) {
                    return 1;
                } else if (product1UnitPrice == null) {
                    return -1;
                } else {

                    if (!CollectionUtils.isEmpty(product1.getQuantityPriceList())) {
                        Product.QuantityPrice quantityPrice = product1.getQuantityPriceList().get(0);
                        if (quantityPrice.getUnitPrice() != null) {
                            product1UnitPrice = quantityPrice.getUnitPrice();
                        }
                    }

                    if (!CollectionUtils.isEmpty(product2.getQuantityPriceList())) {
                        Product.QuantityPrice quantityPrice = product2.getQuantityPriceList().get(0);
                        if (quantityPrice.getUnitPrice() != null) {
                            product2UnitPrice = quantityPrice.getUnitPrice();
                        }
                    }

                    return product1UnitPrice.compareTo(product2UnitPrice);
                }
            default:
                return product1.getPrice().compareTo(product2.getPrice());
        }
    }
}
