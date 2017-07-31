package com.groceriescoach.core.domain.pack;

import java.io.Serializable;

public abstract class Pack implements Serializable{

    private static final long serialVersionUID = -2183090914092390597L;
    private String packSize;
    private Integer packSizeInt;
    private Double unitPrice;
    private String unitSize;
    private String unitPriceStr;

    abstract boolean hasUnitPrice();


    public String getPackSize() {
        return packSize;
    }

    public void setPackSize(String packSize) {
        this.packSize = packSize;
    }

    public Integer getPackSizeInt() {
        return packSizeInt;
    }

    public void setPackSizeInt(Integer packSizeInt) {
        this.packSizeInt = packSizeInt;
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
}
