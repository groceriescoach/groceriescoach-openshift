package com.groceriescoach.core.domain.pack;

import com.groceriescoach.core.domain.GroceriesCoachProduct;

import static com.groceriescoach.core.com.groceriescoach.core.utils.CurrencyUtils.formatCurrencyAmount;

public class SinglePack extends Pack {

    private static final long serialVersionUID = -834446404265732208L;

    @Override
    public boolean hasUnitPrice() {
        return false;
    }

    @Override
    protected void updateUnitPricesInQuantityPrice(GroceriesCoachProduct.QuantityPrice quantityPrice) {
        quantityPrice.setUnitPrice((quantityPrice.getPrice() * 100) / quantityPrice.getQuantity());
        quantityPrice.setUnitPriceStr(formatCurrencyAmount(quantityPrice.getUnitPrice()) + " Each");
        quantityPrice.setUnitSize("Each");
    }
}
