package com.groceriescoach.core.domain.pack;

import com.groceriescoach.core.domain.GroceriesCoachProduct;

import java.io.Serializable;
import java.util.List;

public abstract class Pack implements Serializable{

    private static final long serialVersionUID = -2183090914092390597L;
    private String packSizeStr;
    private Double packSize;
    private Double unitPrice;
    private String unitSize;
    private String unitPriceStr;
    private Double priceInCents;

    Pack() {
    }

    public Pack(Pack pack) {
        this.packSizeStr = pack.getPackSizeStr();
        this.packSize = pack.getPackSize();
        this.unitPrice = pack.getUnitPrice();
        this.unitSize = pack.getUnitSize();
        this.unitPriceStr = pack.getUnitPriceStr();
        this.priceInCents = pack.getPriceInCents();
    }

    abstract boolean hasUnitPrice();


    public String getPackSizeStr() {
        return packSizeStr;
    }

    public void setPackSizeStr(String packSizeStr) {
        this.packSizeStr = packSizeStr;
    }

    public Double getPackSize() {
        return packSize;
    }

    public void setPackSize(Double packSize) {
        this.packSize = packSize;
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

    public Double getPriceInCents() {
        return priceInCents;
    }

    public void setPriceInCents(Double priceInCents) {
        this.priceInCents = priceInCents;
    }

    public void updateUnitPricesInQuantityPriceList(List<GroceriesCoachProduct.QuantityPrice> quantityPriceList) {
        for (GroceriesCoachProduct.QuantityPrice quantityPrice : quantityPriceList) {
            updateUnitPricesInQuantityPrice(quantityPrice);
        }
    }

    protected abstract void updateUnitPricesInQuantityPrice(GroceriesCoachProduct.QuantityPrice quantityPrice);
}
